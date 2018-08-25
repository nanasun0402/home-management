//package com.caring.service.csv;
//
//
//import com.caring.dao.config.DaoUtils;
//import com.caring.dao.model.Followup;
//import com.caring.dao.model.FormDefinition;
//import com.caring.dao.service.FollowupService;
//import com.caring.dao.service.FormDefinitionService;
//import com.caring.service.CaringServiceException;
//import com.caring.service.ServiceUtils;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.opencsv.CSVWriter;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.CharArrayWriter;
//import java.io.Writer;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 通用csv导出类
// *
// * @author eddie
// * @since 2018-03-09
// */
//@Service
//public class CVSExportService {
//
//    /**
//     * 导出Csv
//     *
//     * @param formDefinitionId
//     * id
//     * @Param reqStartTime
//     * 开始时间
//     * @Param reqEndTime
//     * 结束时间
//     * @result byte[]
//     * 要导出的二进制数据
//     */
//    @Autowired
//    private FollowupService followupService;
//    @Autowired
//    private FormDefinitionService formDefinitionService;
//
//    private final Logger LOG = Logger.getLogger(CVSExportService.class);
//
//    public byte[] followupCVSExport(Long formDefinitionId, String reqStartTime, String resEndTime) {
//        Date startTime = ServiceUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), reqStartTime);
//        Date endTime = ServiceUtils.parse(new SimpleDateFormat("yyyy-MM-dd"), resEndTime);
//        //根据formDefinitionId获取表单问题
//        List<Followup> list = followupService.findFollowupByDefinitionId(formDefinitionId, startTime, endTime);
//        FormDefinition formDefinition = formDefinitionService.findFormDefinitionById(formDefinitionId);
//        StringBuilder colName = questionName(formDefinition);
//        String[] colNameArr = colName.toString().split(" ");
//        CSVWriter csvWriter;
//        Writer writer = new CharArrayWriter();
//        try {
//            csvWriter = new CSVWriter(writer);
//            csvWriter.writeNext(colNameArr);
//            for (int i = 0; i < list.size(); i++) {
//                if (StringUtils.isNotEmpty(list.get(i).getContent())) {
//                    JsonNode followupJson = DaoUtils.fromJson(list.get(i).getContent());
//                    List<String> followupList = convertToAttributeValueList(followupJson);
//                    StringBuilder followupBuilder = new StringBuilder();
//                    followupBuilder.append(list.get(i).getCustomer().getId() + " "
//                            + list.get(i).getCustomer().getName()).append(" ");
//                    for (int j = 0; j < followupList.size(); j++) {
//                        followupBuilder.append(followupList.get(j)).append(" ");
//                    }
//                    csvWriter.writeNext(followupBuilder.toString().split(" "));
//                }
//            }
//
//            //序列化csv文件数据
//            return writer.toString().getBytes("UTF-8");
//        } catch (Exception e) {
//            LOG.error(e.fillInStackTrace());
//            throw new CaringServiceException("生成csv文件错误");
//        }
//
//    }
//
//    public StringBuilder questionName(FormDefinition formDefinition) {
//        /**
//         * 导出Csv
//         *
//         * @param formDefinition
//         *            要处理的数据
//         * @result colName
//         *            生成csv文件的列头数据
//         */
//        StringBuilder colName = new StringBuilder();
//        if (Objects.nonNull(formDefinition)) {
//            if (StringUtils.isNotEmpty(formDefinition.getContent())) {
//                JsonNode formDefineJson = DaoUtils.fromJson(formDefinition.getContent());
//                List<String> titleList = convertToAttributeTitleList(formDefineJson);
//                colName.append("问诊者ID 问诊者姓名").append(" ");
//                for (int i = 0; i < titleList.size(); i++)
//                    colName.append(titleList.get(i)).append(" ");
//            }
//        }
//        return colName;
//    }
//
//    private List<String> convertToAttributeTitleList(JsonNode followupJson) {
//        List<String> titleList = new ArrayList<>();
//        if (followupJson.isArray()) {
//            ArrayNode formDefineItems = (ArrayNode) followupJson;
//            for (JsonNode item : formDefineItems) {
//                titleList.add(item.get("title").asText());
//            }
//        }
//        return titleList;
//    }
//
//    private List<String> convertToAttributeValueList(JsonNode followupJson) {
//        List<String> attributeDictList = new ArrayList<>();
//        if (followupJson.isObject()) {
//            Iterator<Map.Entry<String, JsonNode>> it = followupJson.fields();
//            while (it.hasNext()) {
//                Map.Entry<String, JsonNode> entry = it.next();
//                attributeDictList.add(entry.getValue().asText());
//            }
//        }
//        return attributeDictList;
//    }
//
//
//}