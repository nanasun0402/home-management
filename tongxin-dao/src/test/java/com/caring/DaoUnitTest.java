//package com.caring;
//
//import com.caring.dao.config.DaoUtils;
//import com.caring.dao.config.JPAConfig;
//import com.caring.dao.model.Image;
//import com.caring.dao.model.User;
//import com.caring.dao.model.query.Page;
//import com.caring.dao.model.query.PageParam;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import com.caring.dao.repository.ImageRepository;
//import com.caring.dao.repository.RoleRepository;
//import com.caring.dao.service.SysUserService;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.google.common.collect.Sets;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Arrays;
//import java.util.List;
//import org.dozer.DozerBeanMapper;
//import org.junit.Ignore;
//
///**
// *
// * @author james
// */
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = JPAConfig.class)
//public class DaoUnitTest {
//
//    protected final Logger LOG = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private ImageRepository imageRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private DozerBeanMapper daoDozerBeanMapper;
//
////    @Test
////    @Ignore
////    public void testSaveIllness() throws Exception {
////        LOG.info("Date match: {}", DaoUtils.matchesStandardDateFormat("2018-02-30"));
////        String content = "{\"whereJoinFollowup\":\"电话、短信或微信通知\",\"outpatientNumber\":\"121212\",\"admissionNumber\":\"无或不记得了\",\"bedNumber\":\"121212\",\"bedDoctor\":{\"1\":true,\"2\":true,\"3\":true,\"4\":true},\"operateDoctor\":{\"1\":true,\"2\":true},\"attendingDoctor\":{\"1\":true,\"2\":true},\"illnessName\":{\"0\":true,\"1\":true},\"diagnosisWay\":{\"1\":true,\"2\":true,\"3\":true},\"isUseThese\":{\"2\":\"测试设备\"},\"firstVisitTime\":\"2017-08-09\",\"operationTime\":\"2018-02-02\",\"leaveHospitalTime\":{\"1\":true},\"emergencyOperation\":\"否\"}";
////        String formDefine = "[{\"groupTitle\":\"\",\"title\":\"从哪里扫码加入随访的\",\"key\":\"whereJoinFollowup\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"门诊时\",\"isOther\":false},{\"title\":\"住院时或出院时\",\"isOther\":false},{\"title\":\"电话、短信或微信通知\",\"isOther\":false},{\"title\":\"其他方式，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"记录一下门诊号（防止丢失，方便医生查资料）\",\"key\":\"outpatientNumber\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"无或不记得了\",\"isOther\":false},{\"title\":\"有，请点此填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"记录一下住院号（防止丢失，方便资料查询）\",\"key\":\"admissionNumber\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"无或不记得了\",\"isOther\":false},{\"title\":\"有，请点此填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"床位（例如：16）\",\"key\":\"bedNumber\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"无或不记得了\",\"isOther\":false},{\"title\":\"有，请点此填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"我的床位医生（可多选）\",\"key\":\"bedDoctor\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"陈洁\",\"isOther\":false},{\"title\":\"辛渊\",\"isOther\":false},{\"title\":\"方旭华\",\"isOther\":false},{\"title\":\"翟丰\",\"isOther\":false},{\"title\":\"何珊\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"我的主刀医生（可多选）\",\"key\":\"operateDoctor\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"没手术过\",\"isOther\":false},{\"title\":\"陈洁\",\"isOther\":false},{\"title\":\"辛渊\",\"isOther\":false},{\"title\":\"方旭华\",\"isOther\":false},{\"title\":\"翟丰\",\"isOther\":false},{\"title\":\"何珊\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"我的主诊医生（可多选）\",\"key\":\"attendingDoctor\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"陈洁\",\"isOther\":false},{\"title\":\"辛渊\",\"isOther\":false},{\"title\":\"方旭华\",\"isOther\":false},{\"title\":\"翟丰\",\"isOther\":false},{\"title\":\"何珊\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"疾病诊断名称（可多选）\",\"key\":\"illnessName\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"儿童阻塞性睡眠呼吸暂停综合征\",\"isOther\":false},{\"title\":\"耳聋\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"诊疗方式（可多选）\",\"key\":\"diagnosisWay\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"没有诊疗\",\"isOther\":false},{\"title\":\"单独腺样体消融\",\"isOther\":false},{\"title\":\"单独扁桃体消融\",\"isOther\":false},{\"title\":\"腺样体消融+扁桃体消融\",\"isOther\":false},{\"title\":\"腺样体消融+扁桃体减容\",\"isOther\":false},{\"title\":\"人工耳蜗植入\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"是否使用以下特殊药物或者医疗设备（可多选）\",\"key\":\"isUseThese\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"\",\"isOther\":false},{\"title\":\"\",\"isOther\":false},{\"title\":\"其他，请填写\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"首次就诊日期（填写事例：2017年10月20日，则填写20171020）\",\"key\":\"firstVisitTime\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"没就诊\",\"isOther\":false},{\"title\":\"有，请在此填写首次就诊日期（例如20171020）\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"手术日期（填写事例：2017年10月20日，则填写20171020）\",\"key\":\"operationTime\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"没手术\",\"isOther\":false},{\"title\":\"有，请在此填写手术日期（例如20171020）\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"出院日期（填写事例：2017年10月20日，则填写20171020）\",\"key\":\"leaveHospitalTime\",\"type\":\"multiSelect\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"从来没住院过\",\"isOther\":false},{\"title\":\"还没出院（出院后记得重新选择）\",\"isOther\":false},{\"title\":\"请在此填写出院日期（例如20171020）\",\"isOther\":true}]},{\"groupTitle\":\"\",\"title\":\"是否急诊手术\",\"key\":\"emergencyOperation\",\"type\":\"select\",\"required\":true,\"hide\":false,\"options\":[{\"title\":\"是\",\"isOther\":false},{\"title\":\"否\",\"isOther\":false}]}]";
////        Illness illness = new Illness();
////        illness.setContent(content);
////        FormDefinition formDefinition = new FormDefinition();
////        formDefinition.setContent(formDefine);
////        //illnessService.parseFields(formDefinition, illness);
////    }
//
////    @Test
////    @Ignore
////    public void testOrFilterIssue() throws IOException {
////        ObjectMapper JSON = new ObjectMapper();
////        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
////        String jsonFilter = "{"
////                + "  \"detail\" : false,"
////                + "  \"size\" : 10,"
////                + "  \"number\" : 0,"
////                + "  \"filter\" : {"
////                + "    \"or\" : false,"
////                + "    \"groupId\" : 10001,"
////                + "    \"next\" : {"
////                + "      \"or\" : true,"
////                + "      \"name\" : \"maso\","
////                + "      \"babyName\" : \"maso\","
////                + "      \"mobile\" : \"maso\""
////                + "    }"
////                + "  }"
////                + "}";
////        PageParam<CustomerFilter> pageParam = JSON.readValue(jsonFilter, new TypeReference<PageParam<CustomerFilter>>() {
////                                                     });
////        Page<Customer> result = customerService.findCustomers(pageParam);
////        LOG.info("############: {}", result.getTotal());
////    }
//
////    @Test
////    @Ignore
////    public void testUpdateRole() {
////        //User user = new User();
////        //user.setUsername("test1");
////        User user = sysUserService.findByUsername("test");
////        //user.setUsername("test1");
////        user.setAuthorities(Sets.newHashSet());
////        sysUserService.saveUser(user);
////    }
//
////    @Test
////    @Ignore
////    public void testOffceChange() {
////        Long doctorId = 10020L;
////        Long officeId = 10075L;
////        Doctor doctor = new Doctor();
////        doctor.setId(doctorId);
////        Office office = new Office();
////        office.setId(officeId);
////        doctor.setOffice(office);
////        doctor = doctorService.saveDoctor(doctor);
////        LOG.info(doctor.getName());
////    }
//
////    @Test
////    @Ignore
////    public void testUpdateHistory() {
////        Long historyId = 10000L;
////        History history = historyRepository.findOne(historyId);
////        history.setGrade(1);
////        history.setLevel(2);
////        history.setReply("aaaaaa");
////    }
//
////    @Test
////    @Ignore
////    public void testDoctorUpdate() {
////        Doctor doctor = new Doctor();
////        doctor.setMobile("223232323111");
////        doctor.setName("bug test");
////        doctor = doctorService.saveDoctor(doctor);
////        LOG.info("created: {}", doctor.getId());
////        doctor.setName(null);
////        doctor.setMobile("1111111111");
////        doctor = doctorService.saveDoctor(doctor);
////        LOG.info("created: {}, mobile:{}, name:{}", doctor.getId(), doctor.getMobile(), doctor.getName());
////    }
//
//    @Test
//    @Ignore
//    public void testStatusFilter() {
//        InquiryFilter filter = new InquiryFilter();
//        filter.setStatus("closed");
//        //filter.setStatuses(Arrays.asList("open", "agree"));
//        LOG.info("InquiryFilter:{}", filter.buildFilter().getExternalFilter());
//    }
//
//    @Test
//    @Ignore
//    public void testInsertCustomer() {
//        Customer customer = new Customer();
//        Child child = new Child();
//        child.setName("test baby");
//        customer.setName("test");
//        customer.setChild(child);
//        customerRepsitory.save(customer);
//    }
//
//    @Test
//    @Ignore
//    public void testInsertImage() {
//        Image image = new Image();
//        image.setName("testimage1");
//        imageRepository.save(image);
//        image.setName("testimage2");
//        imageRepository.save(image);
//    }
//
//    @Test
//    @Ignore
//    public void testInsert1To1() {
//        Inquiry inquiry = new Inquiry();
//        Customer customer = new Customer();
//        customer.setId(10005L);
//        inquiry.setCustomer(customer);
//        Image image = new Image();
//        image.setId(10000L);
//        Image image1 = new Image();
//        image1.setId(10001L);
//        Question question = new Question();
//        question.setImages(Arrays.asList(image, image1));
//        inquiry.setQuestion(question);
//        inquiryRepository.save(inquiry);
//    }
//
//    @Test
//    @Ignore
//    public void testInsertDoctor() {
//        this.buildDemoData();
//    }
//
//    private void buildDemoData() {
//        hospitalRepository.deleteAll();
//        for (long i = 0; i < 3; ++i) {
//            Hospital hospital = new Hospital();
//            hospital.setId(i);
//            hospital.setName(String.format("上海第%d医院", i));
//            hospitalRepository.save(hospital);
//        }
//        List<Hospital> hospitals = hospitalRepository.findAll();
//        officeRepository.deleteAll();
//        for (long i = 0; i < 5; ++i) {
//            Office office = new Office();
//            office.setName(String.format("第%s科室", i));
//            office.setHospital(hospitals.get(0));
//            officeRepository.save(office);
//        }
//    }
//
//    public DaoUnitTest() {
//    }
//
//    @Test
//    @Ignore
//    public void merge() throws IllegalAccessException, InvocationTargetException {
//        Doctor doctor = new Doctor();
//        doctor.setName("123");
//        Office office = new Office();
//        office.setId(0L);
//        office.setName("test office123");
//        doctor.setOffice(office);
//        Doctor doctor2 = new Doctor();
//        doctor2.setName("1111");
//        doctor2.setDuty("test");
//        office = new Office();
//        office.setId(1L);
//        office.setName("test office");
//        doctor2.setOffice(office);
//        daoDozerBeanMapper.map(doctor, doctor2);
//        System.out.println(doctor2.getDuty() + ":" + doctor2.getOffice().getId() + ":" + doctor2.getOffice().getName());
//    }
//
//    @Test
//    @Ignore
//    public void testEmpty() {
//    }
//
//}
