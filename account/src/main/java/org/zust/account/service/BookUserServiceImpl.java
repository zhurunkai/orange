package org.zust.account.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.BookUserDao;
import org.zust.account.dao.SaltDao;
import org.zust.account.dao.TabDao;
import org.zust.account.dao.TabWeightDao;
import org.zust.account.entity.BookUserEntity;
import org.zust.account.entity.SaltEntity;
import org.zust.account.entity.TabEntity;
import org.zust.account.entity.TabWeightEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.account.utils.RandomName;
import org.zust.account.utils.RandomProfile;
import org.zust.interfaceapi.dto.*;
import org.zust.interfaceapi.service.*;
import org.zust.interfaceapi.utils.ResType;

import java.util.*;

@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class BookUserServiceImpl implements BookUserService {
    @Autowired
    private SaltDao saltDao;

    @Autowired
    private BookUserDao bookUserDao;

    @Autowired
    private TabWeightDao tabWeightDao;

    @Autowired
    private TabDao tabDao;

    @Autowired
    private TabService tabService;

    @Autowired
    private AdUserService adUserService;

    @Reference(check = false)
    private RecommendService recommendService;

    @Reference(check = false)
    private BookShelfService bookShelfService;

    @Reference(check = false)
    private BookService bookService;


    //读者登录所用验证码
    @Override
    public ResType identifyCode(Map param) {
        Map saltdata = IdentifyingCode.execute((String) param.get("phone"));
        if ((int) saltdata.get("status") == 0) {
            return new ResType(400, 101);
        }
        String salt = (String) saltdata.get("salt");
        String randomcode = (String) saltdata.get("randomCode");
        String phone = (String) param.get("phone");

        SaltEntity data = new SaltEntity(phone, salt, randomcode);
        SaltEntity save = saltDao.save(data);

        return new ResType(save);
    }

    @Override
    public ResType lrBookUser(Map param) {

        try {
            String phone = (String) param.get("phone");
            String salt = (String) param.get("salt");
            String captcha = (String) param.get("captcha");

            SaltEntity yanzheng = saltDao.findOneBy(phone, salt, captcha);
            System.out.println(yanzheng);
            if (yanzheng != null) {

                BookUserEntity bookUserEntity = bookUserDao.findByPhone(phone);
                if (bookUserEntity == null) {
                    String randomProfile = RandomProfile.profileString();
                    Integer age = 0;
                    String randomName = RandomName.nameString();
                    String sex = "男";
                    String token1 = IdentifyingCode.md5(phone + new Date().getTime());

                    BookUserEntity data = new BookUserEntity(phone, randomProfile, age, randomName, sex, token1);
                    BookUserEntity save = bookUserDao.save(data);

                    HashMap<String, Object> Map = new HashMap<>();
                    Map.put("name", "默认书架");
                    Map.put("owner", save.getId());


                    bookShelfService.addBookShelf(Map);

                    return new ResType(e2d(save));

                } else {
                    String token2 = IdentifyingCode.md5(phone + new Date().getTime());
                    BookUserEntity bookUserEntity1 = bookUserDao.findByPhone(phone);
                    bookUserEntity1.setToken(token2);

                    BookUserEntity data = bookUserEntity1;
                    BookUserEntity save = bookUserDao.save(data);
                    return new ResType(e2d(bookUserEntity1));
                }
            } else {
                return new ResType(500, 111);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(400, 107);
        }


    }

    //通过id查找读书人的所有信息
    @Override
    public ResType findBookUserAllInformById(int buId) {
        try {
            BookUserEntity busere = bookUserDao.findOneById(buId);
            return new ResType(e2d(busere));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    @Override
    public ResType findTabsByBuid(int id) {

        try {
            List<TabWeightEntity> tabWeightEntities = tabWeightDao.findAllByUser(id);
            HashMap<Integer, String> map = new HashMap<>();
            List<Integer> tabIds = new ArrayList<>();
            for (TabWeightEntity t : tabWeightEntities) {
                tabIds.add(t.getTab());
            }
            List<TabEntity> tabEntities = tabDao.findAllByIds(tabIds);
            for (TabEntity tabEntity : tabEntities) {
                map.put(tabEntity.getId(), tabEntity.getName());
            }
            return new ResType(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }


    }

    @Override
    public ResType chooseTags(int id, Map param) {
        try {
            ResType tag = tabService.findTagIdByName(param);
            ArrayList data = (ArrayList) tag.getData();
            ArrayList allData = new ArrayList();
            for (Object datum : data) {
                Integer weight = 100;
                TabWeightEntity tabWeightEntity = new TabWeightEntity(id, (Integer) datum, weight);
//                System.out.println(tabWeightEntity);
                TabWeightEntity save = tabWeightDao.save(tabWeightEntity);
//                System.out.println(save);
                ResType bookuser = findBookUserAllInformById(id);
                ResType tags = findTabById((Integer) datum);

                BookUserDto bookUserDto = (BookUserDto) bookuser.getData();
                TabDto tabDto = (TabDto) tags.getData();

                TabWeightDto tabWeightDto = e2d(save);
                tabWeightDto.setUser(bookUserDto);
                tabWeightDto.setTab(tabDto);

                allData.add(tabWeightDto);

            }
//            System.out.println(allData);
            return new ResType(allData);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 103);
        }

    }

    @Override
    public ResType findTabWeightByBuid(int id) {
        try {
            List<TabWeightEntity> tabWeightEntities = tabWeightDao.findAllByUser(id);
            List<TabWeightDto> tabWeightDtos = new ArrayList<>();
            for (TabWeightEntity t : tabWeightEntities) {
                tabWeightDtos.add(new TabWeightDto(t.getId(), (BookUserDto) findBookUserAllInformById(t.getUser()).getData(), (TabDto) findTabById(t.getTab()).getData(), t.getWeight()));
            }
            return new ResType(tabWeightDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    private BookUserDto e2d(BookUserEntity bookUserEntity) {
        if (bookUserEntity == null)
            return null;
        BookUserDto bookUserDto = new BookUserDto();
        BeanUtils.copyProperties(bookUserEntity, bookUserDto);
        return bookUserDto;
    }

    private TabWeightDto e2d(TabWeightEntity tabWeightEntity) {
        if (tabWeightEntity == null)
            return null;
        TabWeightDto tabWeightDto = new TabWeightDto();
        BeanUtils.copyProperties(tabWeightEntity, tabWeightDto);
        return tabWeightDto;
    }

    private BookUserEntity d2e(BookUserDto bookUserDto) {
        if (bookUserDto == null)
            return null;
        BookUserEntity bookUserEntity = new BookUserEntity();
        BeanUtils.copyProperties(bookUserDto, bookUserEntity);
        return bookUserEntity;
    }

    // 根据tabid获得tabDto
    public ResType findTabById(Integer id) {
        TabEntity tabEntity = tabDao.findOneById(id);
        if (tabEntity == null) {
            return new ResType(500, 101);
        }
        TabDto tabDto = new TabDto();
        BeanUtils.copyProperties(tabEntity, tabDto);
        return new ResType(tabDto);
    }

    @Override
    public ResType getRecommendAd(Integer id, Integer bookId) {
        try {
            ResType res = recommendService.adRecommendByUserTab(id);
            if (res.getStatus() != 200) {
                return res;
            }
            ResType res1 = adUserService.updateMoney(0.1, ((AdvertisementDto) res.getData()).getOwner().getId());
            ResType res2 = adUserService.addThrow("查看", 0.1, id, ((AdvertisementDto) res.getData()).getId(), bookId);
            if (res1.getStatus() == 200 && res2.getStatus() == 200) {
                return res;
            }
            return new ResType(500, 108);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 108);
        }
    }

    @Override
    public ResType clickAd(Integer id, Integer bookId, Integer adId) {
        try {
            ResType res2 = adUserService.addThrow("点击", 1.0, id, adId, bookId);
            if (res2.getStatus() == 200) {
                return res2;
            }
            return new ResType(500, 108);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 108);
        }
    }

    public ResType getBookRecommendByTab(Integer id) {
        try {
            List<TabWeightEntity> tabWeightEntities = tabWeightDao.findAllByUser(id);
            if (tabWeightEntities.size() == 0) {
                // 返回1-5本最多的书
            }
            // 如果他少于3个大于50的标签
            Collections.sort(tabWeightEntities, new Comparator<TabWeightEntity>() {
                @Override
                public int compare(TabWeightEntity user1, TabWeightEntity user2) {
                    Integer id1 = user1.getWeight();
                    Integer id2 = user2.getWeight();
                    //可以按User对象的其他属性排序，只要属性支持compareTo方法
                    return id2.compareTo(id1);
                }
            });
            //
            int count = 0;
            for (int i = 0; i < tabWeightEntities.size(); i++) {
                if (tabWeightEntities.get(i).getWeight() > 50) {
                    count++;
                }
            }
            // 获取1-5的书
            ResType bookIdByMostAddRes = bookService.getBookIdByMostAdd(5);
            if (bookIdByMostAddRes.getStatus() != 200) {
                return bookIdByMostAddRes;
            }
            List<Integer> book1to5 = (List<Integer>) (bookIdByMostAddRes.getData());
            if (count < 3) {
                // 直接推荐1-5的书并返回
                ResType bookDto1to5Res = bookService.bookIdsToBookDtos(book1to5);
                if (bookDto1to5Res.getStatus() != 200) {
                    return bookDto1to5Res;
                }
                List<BookDto> bookDtos = (List<BookDto>) (bookDto1to5Res.getData());
                return new ResType(bookDtos);
            }
            List<Integer> tabIds = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                tabIds.add(tabWeightEntities.get(j).getTab());
            }
            // 把tabIds传过去获得书的ids
            ResType getBookIdsByTabIdsRes = bookService.getBookIdsByTabIds(tabIds);
            if (getBookIdsByTabIdsRes.getStatus() != 200) {
                return new ResType(500, 108);
            }
            List<Integer> bookIdsByTabIds = (List<Integer>) (getBookIdsByTabIdsRes.getData());
            if (bookIdsByTabIds.size() > 5) {
                List<Integer> finalBookIdsBeyond5 = bookIdsByTabIds.subList(0, 4);
                ResType finalBookIdsBeyond5Res = bookService.bookIdsToBookDtos(finalBookIdsBeyond5);

                return finalBookIdsBeyond5Res;

            }
            int index = 0;
            while (bookIdsByTabIds.size() < 5) {
                if (!bookIdsByTabIds.contains(book1to5.get(index))) {
                    bookIdsByTabIds.add(book1to5.get(index));
                }
                index++;
            }
            ResType finalBookIdsByTabsRes = bookService.bookIdsToBookDtos(bookIdsByTabIds);

            return finalBookIdsByTabsRes;

        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 108);
        }
    }
}
