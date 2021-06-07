package org.zust.recommend.service;

import org.springframework.stereotype.Service;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.RecommendService;
import org.zust.interfaceapi.utils.ResType;
import org.zust.recommend.dto.BookId2BuserIdDto;

import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {

    // 基于用户的协同过滤算法
    public ResType userBasedCF() {
//        获取所有的chain
        List<BookChainDto> lists = new ArrayList<>();
        lists.add(new BookChainDto(1,"林炫宇天王","nih",new BookShelfDto(1,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(2,"林炫宇天王","nih",new BookShelfDto(2,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(3,"林炫宇天王","nih",new BookShelfDto(3,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(3,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(4,"林炫宇天王","nih",new BookShelfDto(4,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(4,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(5,"林炫宇天王","nih",new BookShelfDto(5,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(6,"林炫宇天王","nih",new BookShelfDto(6,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(7,"林炫宇天王","nih",new BookShelfDto(7,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(8,"林炫宇天王","nih",new BookShelfDto(8,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(9,"林炫宇天王","nih",new BookShelfDto(9,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(10,"林炫宇天王","nih",new BookShelfDto(10,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(3,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(11,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(12,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(13,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(7,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(14,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(15,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(2,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(16,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(3,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(17,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(6,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(18,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(4,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(7,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(19,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(20,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(5,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(21,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(1,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(22,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(2,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));
        lists.add(new BookChainDto(23,"林炫宇天王","nih",new BookShelfDto(11,"默认",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),0),new BookUserDto(6,"213","21",12,"dfa","男","jkfdak"),20,1,new BookDto(5,"Dfa",new BookUserDto(1,"213","21",12,"dfa","男","jkfdak"),"dfas","daf",1,"dfas","dfa")));

        // 对初始chain集合进行降维，获得BookId2BuserIDto
        List<BookId2BuserIdDto> bookId2BuserIdDtos = dimensionReduction2UserBasedCF(lists);
        // 改造为（用户id：书集合）的map
        HashMap<Integer,List<Integer>> buserId2BookCollection = new HashMap<>();
        for(BookId2BuserIdDto bookId2BuserIdDto: bookId2BuserIdDtos) {
            if(buserId2BookCollection.containsKey(bookId2BuserIdDto.getBuserId())) {
                buserId2BookCollection.get(bookId2BuserIdDto.getBuserId()).add(bookId2BuserIdDto.getBookId());
            } else {
                buserId2BookCollection.put(bookId2BuserIdDto.getBuserId(),new ArrayList<>());
            }
        }
//        for(Integer key : buserId2BookCollection.keySet()){
//            System.out.print("Key = "+key+"value = "+buserId2BookCollection.get(key));
//        }
        // 用户相似度计算(以id为3的用户为例，之后根据传值所定)
        Integer buserId = 3;
        // 首先定义一个用户3与其他用户的相似度map，之后进行遍历
        HashMap<Integer,Double> buserSimilarity = new HashMap<>();
        // 填入key，value暂时都为0,除了自己为1
        for(Integer key : buserId2BookCollection.keySet()){
            if(!buserId.equals(key))  {
//                buserSimilarity.put(key,0.0);
                // 当前buser的书数量
                int buserBooks = buserId2BookCollection.get(buserId).size();
                // 当前循环到的用户的书数量
                int cycleUserBooks = buserId2BookCollection.get(key).size();
                // 共同拥有的书数量
                int commonNum = 0;
                for(Integer inKey : buserId2BookCollection.get(buserId)) {
                    if(buserId2BookCollection.get(key).contains(inKey)) {
                        commonNum++;
                    }
                }
                buserSimilarity.put(key,((double)commonNum/buserBooks)*((double)commonNum/cycleUserBooks));
            }

        }
        // 得到用户相似度map之后，排序
        List<Map.Entry<Integer, Double>> similarBusers = new ArrayList<Map.Entry<Integer, Double>>(buserSimilarity.entrySet());
        Collections.sort(similarBusers, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).toString().compareTo(o1.getValue().toString());
            }
        });
        // 取出3个与他最相似的用户
        List<Map.Entry<Integer, Double>> processedSimilarity =similarBusers.subList(0,3);
        // 对HashMap中的 value 进行排序后  显示排序结果
//        for (int i = 0; i < processedSimilarity.size(); i++) {
//            String id = similarBusers.get(i).toString();
//            System.out.print(id + "  ");}
        // 获得相似用户书籍id集合
        List<Integer> similarBooks = new ArrayList<>();
        for (Map.Entry<Integer, Double> map : processedSimilarity) {
            similarBooks.addAll(buserId2BookCollection.get(map.getKey()));
        }
        // 推荐书籍去重
        Set<Integer> similarBooksToSet = new LinkedHashSet<>(similarBooks);
        List<Integer> similarBooksToSetToList = new ArrayList<>(similarBooksToSet);
        // 去除该用户自己已有书籍
        for (int i = similarBooksToSetToList.size()-1; i>=0; i--) {
            if(buserId2BookCollection.get(buserId).contains(similarBooksToSetToList.get(i))) {
                similarBooksToSetToList.remove(i);
            }
        }
        for (Integer similarBook : similarBooksToSetToList) {
            System.out.println(similarBook);
        }
        return new ResType(similarBooksToSetToList);
    }

    // 将BookChain集合降维为BookId2BuserId集合
    public List<BookId2BuserIdDto> dimensionReduction2UserBasedCF(List<BookChainDto> bookChainDtos) {
        List<BookId2BuserIdDto> bookId2BuserIdDtos = new ArrayList<>();
        for (BookChainDto bookChainDto : bookChainDtos) {
            bookId2BuserIdDtos.add(new BookId2BuserIdDto(bookChainDto.getOrigin().getId(),bookChainDto.getOwner().getId()));
        }
        return bookId2BuserIdDtos;
    }
}
