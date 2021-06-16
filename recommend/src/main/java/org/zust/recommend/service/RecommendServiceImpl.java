package org.zust.recommend.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zust.interfaceapi.dto.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.service.RecommendService;
import org.zust.interfaceapi.utils.ResType;
import org.zust.recommend.dto.BookId2BuserIdDto;

import java.util.*;

@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class RecommendServiceImpl implements RecommendService {
    @Reference(check = false)
    private BookUserService bookUserService;
    @Reference(check = false)
    private AdvertisementService advertisementService;

    // 通过读书人id根据用户标签推荐投放的广告
    public ResType adRecommendByUserTab(Integer id) {
        try {
            // 根据id获得用户标签权重信息
            ResType userTabWeight = bookUserService.findTabWeightByBuid(id);
            if(userTabWeight.getStatus()!=200) {
                return userTabWeight;
            }


            List<TabWeightDto> tabWeightDtos = (List<TabWeightDto>)userTabWeight.getData();
            Collections.sort(tabWeightDtos, new Comparator<TabWeightDto>() {
                @Override
                public int compare(TabWeightDto user1, TabWeightDto user2) {
                    Integer id1 = user1.getWeight();
                    Integer id2 = user2.getWeight();
                    //可以按User对象的其他属性排序，只要属性支持compareTo方法
                    return id2.compareTo(id1);
                }
            });
//            for (TabWeightDto tabWeightDto : tabWeightDtos) {
//                System.out.println(tabWeightDto);
//            }
            for (TabWeightDto tabWeightDto : tabWeightDtos) {
                ResType res = advertisementService.getAdvertisementByTabId(tabWeightDto.getTab().getId());
                if(res.getStatus()==200) {
                    List<AdvertisementDto> advertisementDtos = (List<AdvertisementDto>) res.getData();
                    for (AdvertisementDto advertisementDto : advertisementDtos) {
                        // 判断一下钱是不是够用
                        ResType checkBudgetRes = checkAdRecommendByBudget(advertisementDto);
                        if(checkBudgetRes.getStatus()==200) {
                            return new ResType(checkBudgetRes.getData());
                        }
                    }
                }
            }
        return new ResType(500,109);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,110);
        }
    }
    // 判断钱够不够用
    public ResType checkAdRecommendByBudget(AdvertisementDto advertisementDto) {
        try {
            ResType res = advertisementService.getAdvertisementThrow(advertisementDto.getId());
            if(res.getStatus()!=200) {
                return new ResType(500,108);
            }
            List<ThrowRecordsDto> throwRecordsDtos = (List<ThrowRecordsDto>) res.getData();
            Double cost = 0.0;
            for (ThrowRecordsDto throwRecordsDto : throwRecordsDtos) {
                cost+=throwRecordsDto.getCost();
            }
            if((advertisementDto.getBudget()-cost)<1.1) {
                return new ResType(500,108);
            }
            return new ResType(advertisementDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
    }

    // 基于用户的协同过滤算法
    public ResType userBasedCF(Integer id) {
//        获取所有的chain
        List<BookChainDto> bookChainDtos = 获得假数据();
        // 对初始chain集合进行降维，获得BookId2BuserIDto
        List<BookId2BuserIdDto> bookId2BuserIdDtos = dimensionReduction2CF(bookChainDtos);
        // 获得键值map
        HashMap<Integer,List<Integer>> key2ValueCollection = getKey2ValueCollection(bookId2BuserIdDtos,"user");
        // 获得相似id
        List<Integer> similarIds = calcSimilarity(key2ValueCollection,"user",id,3);
        // 创建推荐书籍列表，根据相似用户id，合并用户书籍列表
        List<Integer> similarBooks = new ArrayList<>();
        for (Integer similarId : similarIds) {
            similarBooks.addAll(key2ValueCollection.get(similarId));
        }
        // 推荐书籍去重
        Set<Integer> similarBooksToSet = new LinkedHashSet<>(similarBooks);
        List<Integer> similarBooksToSetToList = new ArrayList<>(similarBooksToSet);
        // 去除该用户自己已有书籍
        for (int i = similarBooksToSetToList.size()-1; i>=0; i--) {
            if(key2ValueCollection.get(id).contains(similarBooksToSetToList.get(i))) {
                similarBooksToSetToList.remove(i);
            }
        }
        // 打印
//        for (Integer similarBook : similarBooksToSetToList) {
//            System.out.println(similarBook);
//        }
        return new ResType(similarBooksToSetToList);
    }

    public ResType itemBasedCF(Integer id) {
        // 获取所有的chain
        List<BookChainDto> bookChainDtos = 获得假数据();
        // 对初始chain集合进行降维，获得BookId2BuserIDto
        List<BookId2BuserIdDto> bookId2BuserIdDtos = dimensionReduction2CF(bookChainDtos);
        // 获得键值map
        HashMap<Integer,List<Integer>> key2ValueCollection = getKey2ValueCollection(bookId2BuserIdDtos,"user");
        // 获得相似id
        List<Integer> similarIds = calcSimilarity(key2ValueCollection,"user",id,3);
        return new ResType(similarIds);
    }
    // 获得键值map
    public HashMap<Integer,List<Integer>> getKey2ValueCollection(List<BookId2BuserIdDto> bookId2BuserIdDtos, String type) {
        // 改造为（用户id：书集合）or（书id：用户集合）的map
        HashMap<Integer,List<Integer>> key2ValueCollection = new HashMap<>();
        for(BookId2BuserIdDto bookId2BuserIdDto: bookId2BuserIdDtos) {
            if("user".equals(type)) {
                if (!key2ValueCollection.containsKey(bookId2BuserIdDto.getBuserId())) {
                    key2ValueCollection.put(bookId2BuserIdDto.getBuserId(), new ArrayList<>());
                }
                key2ValueCollection.get(bookId2BuserIdDto.getBuserId()).add(bookId2BuserIdDto.getBookId());
            } else {
                if (!key2ValueCollection.containsKey(bookId2BuserIdDto.getBookId())) {
                    key2ValueCollection.put(bookId2BuserIdDto.getBookId(), new ArrayList<>());
                }
                key2ValueCollection.get(bookId2BuserIdDto.getBookId()).add(bookId2BuserIdDto.getBuserId());
            }

        }
        return key2ValueCollection;
    }
    
    public List<Integer> calcSimilarity(HashMap<Integer,List<Integer>> key2ValueCollection, String type, Integer id, Integer num) {

        // 相似度计算(以id为3为例，之后根据传值所定)
        // 首先定义一个相似度map，之后进行遍历
        HashMap<Integer,Double> similarityMap = new HashMap<>();
        // 填入key，value暂时都为0,除了自己为1
        for(Integer key : key2ValueCollection.keySet()){
            if(!id.equals(key))  {
                // 当前buser的书数量
                int currentIdValues = key2ValueCollection.get(id).size();
                // 当前循环到的用户的书数量
                int cycleIdValues = key2ValueCollection.get(key).size();
                // 共同拥有的书数量
                int commonNum = 0;
                for(Integer inKey : key2ValueCollection.get(id)) {
                    if(key2ValueCollection.get(key).contains(inKey)) {
                        commonNum++;
                    }
                }
                similarityMap.put(key,((double)commonNum/currentIdValues)*((double)commonNum/cycleIdValues));

            }

        }
        // 得到相似度map之后，排序
        List<Map.Entry<Integer, Double>> similarList = new ArrayList<Map.Entry<Integer, Double>>(similarityMap.entrySet());
        Collections.sort(similarList, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).toString().compareTo(o1.getValue().toString());
            }
        });
        // 取出3个最相似的
        List<Map.Entry<Integer, Double>> processedSimilarity =similarList.subList(0,num);
        // 对HashMap中的 value 进行排序后  显示排序结果
//        for (int i = 0; i < processedSimilarity.size(); i++) {
//            String vid = similarList.get(i).toString();
//            System.out.print(vid + "  ");}
        List<Integer> similarIds = new ArrayList<>();
        for (Map.Entry<Integer, Double> map : processedSimilarity) {
            similarIds.add(map.getKey());
        }
//        System.out.println(similarIds);
        return similarIds;
    }

    // 将BookChain集合降维为BookId2BuserId集合
    public List<BookId2BuserIdDto> dimensionReduction2CF(List<BookChainDto> bookChainDtos) {
        List<BookId2BuserIdDto> bookId2BuserIdDtos = new ArrayList<>();
        for (BookChainDto bookChainDto : bookChainDtos) {
            bookId2BuserIdDtos.add(new BookId2BuserIdDto(bookChainDto.getOrigin().getId(),bookChainDto.getOwner().getId()));
        }
//        for (BookId2BuserIdDto bookId2BuserIdDto : bookId2BuserIdDtos) {
//            System.out.println(bookId2BuserIdDto);
//        }
        return bookId2BuserIdDtos;
    }

    public List<BookChainDto> 获得假数据() {
        List<BookChainDto> lists = new ArrayList<>();
        lists.add(new BookChainDto(1,"林炫宇天王","nih",new BookShelfDto(1,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(2,"林炫宇天王","nih",new BookShelfDto(2,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(3,"林炫宇天王","nih",new BookShelfDto(3,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(3,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(4,"林炫宇天王","nih",new BookShelfDto(4,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(4,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(5,"林炫宇天王","nih",new BookShelfDto(5,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(6,"林炫宇天王","nih",new BookShelfDto(6,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(7,"林炫宇天王","nih",new BookShelfDto(7,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(8,"林炫宇天王","nih",new BookShelfDto(8,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(9,"林炫宇天王","nih",new BookShelfDto(9,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(10,"林炫宇天王","nih",new BookShelfDto(10,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(3,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(11,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(12,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(13,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(7,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(14,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(3,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(15,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(16,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(17,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(18,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(7,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(19,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(20,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(21,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(22,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(23,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
    return lists;
    }
}
