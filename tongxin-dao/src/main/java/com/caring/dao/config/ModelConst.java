package com.caring.dao.config;

/**
 *
 * @author james
 */
public interface ModelConst {

    public static interface Page {

        String PAGE_SIZE = "page_size";
    }

    public static interface Image {

        String ID_FIELD = "fileId";
        String CERTIFICATION = "certificate";
        String AVATAR = "avatar";
        String ILLNESS = "illness";

        String HISTORY_IMAGE_PURPOSE = "history_image_purpose";
        String INQUIRY_IMAGE_PURPOSE = "inquiry_image_purpose";
        String DOCOTOR_HEAD_IMAGE_PURPOSE = "doctor_head_image_purpose";
        String CUSTOMER_HEAD_IMAGE_PURPOSE = "customer_head_image_purpose";
        String INQUIRY_AUDIO_PURPOSE = "inquiry_audio_purpose";
    }

    public static interface Region {

        String PROVINCE = "province";
        String CITY = "city";
        String COUNTY = "county";
        String EXCLUDES = "县,市辖区";
    }

    public static interface Role {

        String ROLE_GROUP_LEADER = "group_leader";
        String ROLE_GROUP_MEMBER = "group_memeber";
    }

    public static interface User {

        String ADMIN = "admin";

        public static interface Who {

            String USER = "sysuser";
            String DOCTOR = "doctor";
            String CUSTOMER = "customer";
            String SUBSCRIBED = "subscribed";
            String NONEDOCTOR = "nonedoctor";
        }

        public static interface Doctor {

            String DOCTOR_INVITED = "invited";
            String DOCTOR_JOINED = "joined";
        }

        public static interface Inquiry {

            String INQUIRY_EDITING = "editing";
            String INQUIRY_OPEN = "open";
            String INQUIRY_CLOSED = "closed";
            String INQUIRY_REJECT = "reject";
            String INQUIRY_AGREE = "agree";

            String INQUIRY_NEED_PAY_YES = "need_pay_yes";
            String INQUIRY_NEED_PAY_NO = "need_pay_no";

            String INQUIRY_PAY_COMPLETE = "pay_complete";
            String INQUIRY_PAY_PENDING = "pay_pending";

            String INQUIRY_TYPE_MESSAGE = "message"; // 留言问诊
            String INQUIRY_TYPE_RESERVATION = "reservation"; // 电话预约
            String INQUIRY_TYPE_SUBSEQUENT = "subsequent"; // 复诊预约
            String INQUIRY_MESSAGE_CLOSED = "message_closed";
            String ILLNESS_REQUIRED = "illness_required";

        }

        public static interface Group {

            String COMMONWEAL = "commonweal";
            String NONE_COMMONWEAL = "none_commonweal";
        }

        public static interface Flag {

            String DEMO = "demo";
            String REMOVED = "removed";
        }

        public static interface CustomerData {

            String ILLNESS = "ill";
            String FOLLOWUP = "followup";
            String HISTORY = "history";
        }

        public static interface FormData {

            String ILL = "ill";
            String FOLLOW = "follow";
        }

        public static interface FormFieldType {

            String SELECT = "select";
            String MULTI_SELECT = "multiSelect";
            String INPUT = "input";
            String DATE = "date";
        }
    }
}
