var async = require('asyncawait/async');
var await = require('asyncawait/await');
const axios = require('axios');
const fs = require('fs');
//const baseURLHost = "http://120.27.19.234/tongxinrs/api";
const baseURLHost = "http://localhost:9091/tongxinrs/api/v1";
axios.defaults.baseURL = baseURLHost;
axios.defaults.headers.post['Content-Type'] = 'application/json';
const X_WX_TOKEN = 'X-WX-AUTH-TOKEN';
const X_MG_TOKEN = 'X-MG-AUTH-TOKEN';
const DOCTOR_INVITED = "invited";
const DOCTOR_JOINED = "joined";
const COMMONWEAL = "commonweal";
const NONE_COMMONWEAL = "none_commonweal";

(async(() => {
    // TODO create base data
    // ***TODO 创建医院数据
    const loginMGUser = () => {
        let result = await(axios.post("/user/login", {
            username: "admin",
            password: "admin@2017"
        }));
        let xTokenRet = result.data;
        axios.defaults.headers.post[X_MG_TOKEN] = xTokenRet.token[X_MG_TOKEN];
        axios.defaults.headers.put[X_MG_TOKEN] = xTokenRet.token[X_MG_TOKEN];
        axios.defaults.headers.get[X_MG_TOKEN] = xTokenRet.token[X_MG_TOKEN];
        console.log("Login", xTokenRet);
    }
    loginMGUser();
    const insertHospitals = () => {
        const count = 20;
        for (let i = count; i < (2 * count); ++i) {
            const hospital = {
                "address": `上海第${i}大道`,
                "dataFlag": "demo",
                "name": `上海第${i}医院`,
            }
            console.log("creating");
            await(axios.put("/hospital", hospital));
            console.log("created");
        }
        console.log("created hospitals");
    }
    // ***TODO 创建科室数据
    const insertOffices = () => {
        let result = await(axios.post("/hospital", {
            filter: {
                dataFlag: "demo"
            }
        }));
        let hospitals = result.data.content;
        let count = 5;
        hospitals.map(hospital => {
            for (let i = 1; i < count; ++i) {
                let office = {
                    "dataFlag": "demo",
                    "hospital": hospital,
                    "name": `第${i}科室:${hospital.name}`,
                }
                await(axios.put("/office", office));
            }
        })

        console.log("created offices");
    }
    // ***TODO 创建医生微信mock数据
    // 确保每个科室有不同医生
    const insertWechatsAndDoctors = () => {
        let result = await(axios.post("/office", {
            filter: {
                dataFlag: "demo"
            }
        }));
        const offices = result.data.content;
        offices.map(office => {
            // TODO create mock wechat and return it 
            // TODO get mobile verify code
            // TODO get mock token and set it to HTTP header
            // TODO call register API and return registered doctor
            // TODO fill doctor info with real name, mobile, office and etc then save
            //***
            const officeDoctorCount = 5;
            for (let i = 0; i < officeDoctorCount; ++i) {
                const time = new Date().getTime();
                const mobile = time;
                console.log("time:", time);
                // ***TODO create doctor
                let doctor = {
                    name: `张医生:${office.id}:${time}`,
                    office: { id: office.id },
                    speciality: "手术",
                    introduction: "国际知名",
                    duty: "chief_physician",
                    mobile: mobile
                };
                console.log("Generated doctor:", doctor);
                doctor = await(axios.put('/doctor', doctor)).data;
                // create mock wechat and return it
                const wechat = await(axios.put("/wechat", {
                    "city": "string",
                    "code": "string",
                    "country": "string",
                    "dataFlag": "demo",
                    "groupId": 0,
                    "headImgUrl": "http://120.27.19.234/default_headimage.png",
                    "lang": "string",
                    "nickname": `今天我休息-${office.id}-${time}`,
                    "openId": `openid-${office.id}-${time}`,
                    "province": "string",
                    "remark": "string",
                    "sex": "男",
                    "subscribe": true,
                    "subscribeTime": 0,
                    "unionId": "string",
                })).data;
                console.log("wechat created", wechat);
                // get mobile verify code
                let verifyCode = await(axios.get(`/register/sms/doctor/${mobile}`)).data;
                let xTokenRet = await(axios.get(`/xtoken?code=${wechat.openId}`)).data;
                axios.defaults.headers.post[X_WX_TOKEN] = xTokenRet.token[X_WX_TOKEN];
                axios.defaults.headers.put[X_WX_TOKEN] = xTokenRet.token[X_WX_TOKEN];
                // register
                doctor = await(axios.post('/register', {
                    who: 'doctor',
                    mobile: mobile,
                    code: verifyCode
                })).data;
                console.log("Completed doctor:", doctor);
            }
        })
    };
    // ***TODO group
    const insertGroup = () => {
        // /api/v1/office/{officeId}/doctor
        let result = await(axios.post("/office", {
            filter: {
                dataFlag: "demo"
            }
        }));
        const offices = result.data.content;
        offices.map(office => {
            let result = await(axios.post(`/office/${office.id}/doctor`, {
                filter: {
                    dataFlag: "demo"
                }
            }));
            const doctorsOfOffice = result.data.content;
            // /api/v1/group
            let group = {
                "dataFlag": "demo",
                "messageFreeLimit": 5,
                "messagePrice": 1,
                "name": `一体化小组-${office.id}`,
                "officeTypes": [
                    {
                        "key": "office_child"
                    }
                ],
                "reservationFreeLimit": 5,
                "reservationPrice": 1,
                "subsequentFreeLimit": 5,
                "subsequentPrice": 1,
                "type": COMMONWEAL
            };
            group = await(axios.put(`/group`, group)).data;
            console.log("group created", group);
            doctorsOfOffice[0].group = { id: group.id };
            doctorsOfOffice[0].joinGroupStatus = DOCTOR_JOINED;
            let doctor = doctorsOfOffice[0];
            console.log("join group:", doctor);
            doctor = await(axios.put(`/doctor`, doctor)).data;
            // TODO invit doctors
            let invited = await(axios.post(`/doctor/${doctor.id}/invite`, { doctorId: doctorsOfOffice[1].id, groupId: group.id })).data;
            console.log("invited:", invited);
        })
    }
    // ***TODO create customer and child
    const createCustomerAndChild = () => {
        const groups = await(axios.post(`/group`)).data.content;
        groups.map(group => {
            // create customer
            const customerCount = 5;
            for (let i = 0; i < customerCount; ++i) {
                const time = new Date().getTime();
                const mobile = time;
                console.log("time:", time);
                // create mock wechat and return it
                const wechat = await(axios.put("/wechat", {
                    "city": "string",
                    "code": "string",
                    "country": "string",
                    "dataFlag": "demo",
                    "groupId": 0,
                    "headImgUrl": "http://120.27.19.234/default_headimage.png",
                    "lang": "string",
                    "nickname": `今天我有病-${group.id}-${time}`,
                    "openId": `openid-${group.id}-${time}`,
                    "province": "string",
                    "remark": "string",
                    "sex": "男",
                    "subscribe": true,
                    "subscribeTime": 0,
                    "unionId": "string",
                })).data;
                console.log("wechat created", wechat);
                // get mobile verify code
                let verifyCode = await(axios.get(`/register/sms/customer/${mobile}`)).data;
                let xTokenRet = await(axios.get(`/xtoken?code=${wechat.openId}`)).data;
                axios.defaults.headers.post[X_WX_TOKEN] = xTokenRet.token[X_WX_TOKEN];
                axios.defaults.headers.put[X_WX_TOKEN] = xTokenRet.token[X_WX_TOKEN];
                // register
                let customer = await(axios.post('/register', {
                    who: 'customer',
                    mobile: mobile,
                    code: verifyCode
                })).data;
                console.log("complete customer register", customer);
                // ***TODO create customer
                customer = Object.assign(customer, {
                    "dataFlag": "demo",
                    "name": `张大🐂-${group.id}-${time}`,
                    "child": {
                        "birth": "2017-10-08",
                        "dataFlag": "demo",
                        "gender": group.id % 2 === 0 ? "male" : "female",
                        "name": `小张大🐂-${group.id}-${time}`
                    }
                })
                if (i < 3) {
                    customer.group = { id: group.id };
                }
                console.log("saveing customer", customer);
                customer = await(axios.put('/customer', customer)).data;
                console.log("Completed customer:", customer);
            }
        });
    }
    // ***TODO create history and illness of customer
    const completeHistoryAndIllness = () => {
        const customers = await(axios.post(`/customer`)).data.content;
        customers.map(customer => {
            let historyCount = 3;
            for (let i = 0; i < historyCount; ++i) {
                const time = new Date().getTime();
                const history = await(axios.put(`/customer/${customer.id}/history`, {
                    description: "有病有病有病吧！！！！" + time
                })).data;
            }
            const time = new Date().getTime();
            const illness = await(axios.put(`/customer/${customer.id}/illness`, {
                content: "{}"
            })).data;
        });
    }

    const setDoctorOfficeType = () => {
        let result = await(axios.post("/doctor"));
        const doctors = result.data.content;
        let count = 0;
        doctors.map(doctor => {
            count++;
            if (count % 2 === 0) {
                doctor = {
                    id: doctor.id,
                    mobile: doctor.mobile,
                    officeType: "office_heart"
                }
            } else {
                doctor = {
                    id: doctor.id,
                    mobile: doctor.mobile,
                    officeType: "office_child"
                }
            }
            await(axios.put("/doctor", doctor));
            console.log("Counting:" + count);
        })
    }

    const setDoctorDuty = () => {
        let result = await(axios.post("/doctor"));
        const doctors = result.data.content;
        let count = 0;
        doctors.map(doctor => {
            ++count;
            await(axios.put("/doctor", { id: doctor.id, duty: 'chief_physician' }));
            console.log("Counting:" + count);
        })
    }
    let request = require('request-promise');
    const postFile = (imageName) => {
        let data = fs.createReadStream(__dirname + (imageName ? imageName : '/default_headimage.png'))
        let form = {
            type: "image/png",
            file: data
        }
        let image = await(request({
            method: 'POST', headers: {
                "X-MG-AUTH-TOKEN": axios.defaults.headers.post[X_MG_TOKEN]
            }, url: `${baseURLHost}/file/history/image`, formData: form
        }))
        console.log(image);
        return JSON.parse(image);
    }
    //uploadFile();
    // ***TODO create inquiry of customer
    const insertInquiries = () => {
        const customers = await(axios.post(`/customer`)).data.content;
        customers.map(customer => {
            for (let i = 0; i < 2; ++i) {
                await(axios.put('/inquiry', {
                    "customer": {
                        id: customer.id
                    },
                    question: {
                        description: "测试测试"
                    },
                    "status": "open",
                    "type": "message",
                }))
            }
        })
    }

    const insertImageInquiries = () => {
        const customers = await(axios.post(`/customer`)).data.content;
        customers.map(customer => {
            await(axios.put('/inquiry', {
                "customer": {
                    id: customer.id
                },
                question: {
                    description: "测试测试带图片",
                    images: [
                        postFile()
                    ]
                },
                "status": "open",
                "type": "message",
            }))
        })
    }

    const setGroupCreatorAndDoctorCreatorFlag = () => {
        const groups = await(axios.post(`/group`)).data.content;
        groups.map(group => {
            const doctorsByGroup = await(axios.post(`/doctor`, { filter: { groupId: group.id } })).data.content;
            if (doctorsByGroup && doctorsByGroup.length > 0) {
                const updatedGroup = await(axios.put(`/group`, { id: group.id.id, creator: { id: doctorsByGroup[0].id } })).data;
                console.log("group:", updatedGroup);
            }
        })
    }

    const fixDoctorOfficeType = () => {
        const doctors = await(axios.post(`/doctor`)).data.content;
        doctors.map(doctor => {
            if (doctor.officeType === 'office_heart') {
                doctor.officeType = 'office_cardiothoracic'
                await(axios.put('/doctor', { id: doctor.id, officeType: doctor.officeType }))
            }
        })
    }

    const testPutIllness = () => {
        let request = { "id": 10407, "customer": { "id": 10807 }, "formDefinition": { "id": 10033 }, "content": "{\"q_3\":\"12312\",\"q_4\":\"asdasdfasdf\"}" }
        let result = await(axios.put(`/customer/10407/illness`, request)).data;
        console.log(result)
    }
    //testPutIllness()
    // fixDoctorOfficeType()
    const createRoles = () => {
        /**
         *  问诊管理  患者管理  医院管理  科室管理  小组管理  医生管理  轮播管理  管理员管理
         */
        let roles = [{
            "key": "role_inquiry",
            "label": "问诊管理"
        }, {
            "key": "role_patient",
            "label": "患者管理"
        }, {
            "key": "role_hospital",
            "label": "医院管理"
        }, {
            "key": "role_office",
            "label": "科室管理"
        }, {
            "key": "role_group",
            "label": "小组管理"
        }, {
            "key": "role_doctor",
            "label": "医生管理"
        }, {
            "key": "role_rotation",
            "label": "轮播管理"
        }, {
            "key": "role_manage",
            "label": "管理员管理"
        }]
        roles.map(role => {
            let result = await(axios.put(`/role`, role)).data;
            console.log(result)
        })
    }
    //createRoles()
    const testRole = () => {
        const request = {
            "id": 10806, "username": "test",
            "authorityIds": ["role_inquiry"], "authorities": [{ "key": "role_inquiry" }]
        }
        let result = await(axios.put(`/user`, request)).data;
        console.log("updated", result)
    }

    const putImageToHistory = () => {
        let image1 = postFile()
        let image2 = postFile()
        let req = {
            "grade": 1,
            "id": 10004,
            "images": [
                { id: image1.id },
                { id: image2.id }
            ],
            "level": 3,
            "reply": "傻逼"
        }
        let result = await(axios.put(`/history`, req)).data
        console.log("Put image to history >>", result)
    }
    putImageToHistory()
    //testRole()
    /*
    insertHospitals();
    insertOffices();
    insertWechatsAndDoctors();
    insertGroup();
    createCustomerAndChild();
    setDoctorOfficeType();
    setDoctorDuty();
    completeHistoryAndIllness();
    insertInquiries();
    insertImageInquiries();
    setGroupCreatorAndDoctorCreatorFlag();//*/
    console.log("Task completed");
}))();
