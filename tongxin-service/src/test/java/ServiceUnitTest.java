//
//import static com.caring.dao.config.ModelConst.Region.CITY;
//import static com.caring.dao.config.ModelConst.Region.COUNTY;
//import static com.caring.dao.config.ModelConst.Region.EXCLUDES;
//import static com.caring.dao.config.ModelConst.Region.PROVINCE;
//import com.caring.dao.model.Region;
//import com.caring.dao.model.query.filter.CustomerFilter;
//import com.caring.dao.repository.RegionRepository;
//import com.caring.service.ServiceUtils;
//import com.caring.service.file.StorageType;
//import com.caring.service.sms.SmsService;
//import com.caring.service.wx.config.WechatMpProperties;
//import com.fasterxml.jackson.databind.JsonNode;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.Charset;
//import java.util.HashSet;
//import java.util.Iterator;
//import me.chanjar.weixin.common.bean.menu.WxMenu;
//import org.apache.commons.io.IOUtils;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// *
// * @author james
// */
//@EnableAutoConfiguration
//@ComponentScan("com.caring")
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ServiceUnitTest {
//
//    protected final Logger LOG = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private SmsService sms235Service;
//    @Autowired
//    private SmsService smsSohuService;
//    @Autowired
//    private RegionRepository regionRepository;
//    @Autowired
//    private WechatMpProperties wxProperties;
//
//    @Test
//    @Ignore
//    public void test() {
//        String test = "ab|cd";
//        String[] tokens = test.split("\\|");
//        LOG.info("{}={}", tokens[0], tokens[1]);
//    }
//
//    @Test
//    @Ignore
//    public void testFilterNext() {
//        CustomerFilter filter = new CustomerFilter();
//        filter.setGroupId(10000L);
//        CustomerFilter or = new CustomerFilter();
//        or.setOr(true);
//        or.setBabyName("testbabyname");
//        or.setMobile("121212");
//        filter.setNext(or);
//        System.out.println("Filter:" + filter.buildFilter().getExternalFilter());
//        String json = ServiceUtils.toJson(filter);
//        filter = ServiceUtils.fromJson(json, CustomerFilter.class);
//        System.out.println("Filter2:" + filter.buildFilter().getExternalFilter());
//    }
//
//    @Test
//    @Ignore
//    public void testMenuJson() throws IOException {
//        String customerMenuJson = IOUtils.toString(this.getClass().getResource(wxProperties.getWxMenuCustomer()), "UTF-8");
//        WxMenu customerMenu = WxMenu.fromJson(customerMenuJson);
//    }
//
//    @Test
//    @Ignore
//    public void testSohuSmsCode() {
//        String code = smsSohuService.sendSmsCode("13818355321", false);
//        LOG.info("send::{}", code);
//        smsSohuService.checkSmsCode("13482718164", code);
//    }
//
//    @Test
//    @Ignore
//    public void testEnum() {
//        LOG.info(StorageType.Audio.name() + ":" + StorageType.Audio.toString());
//        boolean is = StorageType.Image == StorageType.valueOf(StorageType.Image.name());
//        LOG.info(String.valueOf(is));
//    }
//
//    @Test
//    @Ignore
//    public void testImportRegions() throws IOException {
//        try (InputStream in = ServiceUnitTest.class.getResourceAsStream("regions.json")) {
//            String json = IOUtils.toString(in, Charset.forName("UTF-8"));
//            JsonNode node = ServiceUtils.fromJson(json);
//            JsonNode nodeProvinces = node.get("province");
//            Iterator<JsonNode> it = nodeProvinces.iterator();
//            while (it.hasNext()) {
//                // 省，直辖市
//                JsonNode provinceNode = it.next();
//                LOG.info("province:" + provinceNode.get("name").asText());
//                Region province = new Region();
//                province.setName(provinceNode.get("name").asText());
//                province.setZip(provinceNode.get("zip").asText());
//                province.setRegionLevel(PROVINCE);
//                province.setSubRegions(new HashSet<>());
//                JsonNode cities = provinceNode.get("city");
//                if (cities != null) {
//                    Iterator<JsonNode> cityIt = cities.iterator();
//                    while (cityIt.hasNext()) {
//                        // 城市
//                        JsonNode cityNode = cityIt.next();
//                        String cityName = cityNode.get("name").asText();
//                        LOG.info("city:" + cityName);
//                        Region city;
//                        if (!EXCLUDES.contains(cityName)) {
//                            city = new Region();
//                            city.setName(cityNode.get("name").asText());
//                            city.setZip(cityNode.get("zip").asText());
//                            city.setRegionLevel(CITY);
//                            city.setSubRegions(new HashSet<>());
//                            province.getSubRegions().add(city);
//                            city.setParentRegion(province);
//                        } else {
//                            city = province;
//                        }
//                        JsonNode countries = cityNode.get("county");
//                        if (countries != null) {
//                            if (countries.isArray()) {
//                                Iterator<JsonNode> countryIt = countries.iterator();
//                                while (countryIt.hasNext()) {
//                                    // 区，县
//                                    JsonNode countyNode = countryIt.next();
//                                    String countyName = countyNode.get("name").asText();
//                                    LOG.info("county:" + countyName);
//                                    Region county = new Region();
//                                    county.setName(countyName);
//                                    county.setZip(countyNode.get("zip").asText());
//                                    county.setRegionLevel(COUNTY);
//                                    city.getSubRegions().add(county);
//                                    county.setParentRegion(city);
//                                }
//                            } else {
//                                JsonNode countyNode = countries;
//                                String countyName = countyNode.get("name").asText();
//                                LOG.info("county:" + countyName);
//                                Region county = new Region();
//                                county.setName(countyName);
//                                county.setZip(countyNode.get("zip").asText());
//                                county.setRegionLevel(COUNTY);
//                                city.getSubRegions().add(county);
//                                county.setParentRegion(city);
//                            }
//                        }
//                    }
//                }
//                regionRepository.save(province);
//            }
//        }
//    }
//}
