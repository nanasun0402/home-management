package com.caring.wxrs.rest.request;

/**
 *
 * @author james
 */
public class InviteRequest {

    private Long groupId;
    private Long doctorId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

}
