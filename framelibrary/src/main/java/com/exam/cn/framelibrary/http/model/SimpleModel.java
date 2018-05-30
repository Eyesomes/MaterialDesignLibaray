package com.exam.cn.framelibrary.http.model;

import java.util.List;

/**
 * http://27.115.23.126:15032/ashx/mobilenew.ashx?ac=GetMyWaiteApprove&u=1&ukey=abc&pi=0&ps=20
 * Created by 杰 on 2017/10/16.
 */

public class SimpleModel {

    /**
     * error : 0
     * msg : {"total":4,"data":[{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"692","billid":"176","billno":"PTBX1706050008","accobjname":"系统管理员","acctypename":"用户","submituser":"系统管理员","submituid":"1","submitdate":"2017-07-24","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"21403","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170724151621033","buid":"系统管理员","bcreate_uid":"系统管理员","bcreate_date":"2017/6/5 15:15:22","totalmoney":"10.00","prestepid":"2410","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,系统管理员]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"cs","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-06-05","lastapp_date":"2017-07-24 15:16:21","lastapp_uid":"1","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"695","billid":"180","billno":"PTBX1707240002","accobjname":"系统管理员","acctypename":"用户","submituser":"系统管理员","submituid":"1","submitdate":"2017-07-24","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"21398","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170724145745733","buid":"系统管理员","bcreate_uid":"系统管理员","bcreate_date":"2017/7/24 14:57:07","totalmoney":"1000.00","prestepid":"2410","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,系统管理员]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"测试","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-07-24","lastapp_date":"2017-07-24 14:57:45","lastapp_uid":"1","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"578","billid":"65","billno":"PTBX1704120055","accobjname":"系统管理员","acctypename":"用户","submituser":"ERP管理员","submituid":"8","submitdate":"2017-04-12","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"20984","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"审批中","programname":"普通费用报销","programid":"130102","newguid":"20170412134523463","buid":"ERP管理员","bcreate_uid":"ERP管理员","bcreate_date":"2017/4/12 13:40:55","totalmoney":"100.00","prestepid":"","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,崔静]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"打车费用","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-04-12","lastapp_date":"2017-04-16 10:39:05","lastapp_uid":"52","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"598","billid":"70","billno":"PTBX1704150059","accobjname":"系统管理员","acctypename":"用户","submituser":"董志娟","submituid":"67","submitdate":"2017-04-15","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"20969","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170415173546787","buid":"董志娟","bcreate_uid":"董志娟","bcreate_date":"2017/4/15 14:13:25","totalmoney":"400.00","prestepid":"","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,林松]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"1","bdeptid":"行政部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-04-15","lastapp_date":"2017-04-15 17:35:46","lastapp_uid":"67","attstatus":"0","savesourcetype":"PC"}]}
     */

    private int error;
    private MsgBean msg;

    @Override
    public String toString() {
        return "SimpleModel{" +
                "error=" + error +
                ", msg=" + msg.toString() +
                '}';
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * total : 4
         * data : [{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"692","billid":"176","billno":"PTBX1706050008","accobjname":"系统管理员","acctypename":"用户","submituser":"系统管理员","submituid":"1","submitdate":"2017-07-24","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"21403","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170724151621033","buid":"系统管理员","bcreate_uid":"系统管理员","bcreate_date":"2017/6/5 15:15:22","totalmoney":"10.00","prestepid":"2410","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,系统管理员]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"cs","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-06-05","lastapp_date":"2017-07-24 15:16:21","lastapp_uid":"1","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"695","billid":"180","billno":"PTBX1707240002","accobjname":"系统管理员","acctypename":"用户","submituser":"系统管理员","submituid":"1","submitdate":"2017-07-24","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"21398","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170724145745733","buid":"系统管理员","bcreate_uid":"系统管理员","bcreate_date":"2017/7/24 14:57:07","totalmoney":"1000.00","prestepid":"2410","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,系统管理员]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"测试","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-07-24","lastapp_date":"2017-07-24 14:57:45","lastapp_uid":"1","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"578","billid":"65","billno":"PTBX1704120055","accobjname":"系统管理员","acctypename":"用户","submituser":"ERP管理员","submituid":"8","submitdate":"2017-04-12","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"20984","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"审批中","programname":"普通费用报销","programid":"130102","newguid":"20170412134523463","buid":"ERP管理员","bcreate_uid":"ERP管理员","bcreate_date":"2017/4/12 13:40:55","totalmoney":"100.00","prestepid":"","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,崔静]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"打车费用","bdeptid":"人事部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-04-12","lastapp_date":"2017-04-16 10:39:05","lastapp_uid":"52","attstatus":"0","savesourcetype":"PC"},{"companyid":"","flowid":"72","flowcode":"submit","waiteid":"598","billid":"70","billno":"PTBX1704150059","accobjname":"系统管理员","acctypename":"用户","submituser":"董志娟","submituid":"67","submitdate":"2017-04-15","stepid":"2267","stepname":"部门经理","stepmemo":"","steplocation":"Center","dynamicid":"20969","returnrule":"","showurl":"../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1","flowstatus":"已提交","programname":"普通费用报销","programid":"130102","newguid":"20170415173546787","buid":"董志娟","bcreate_uid":"董志娟","bcreate_date":"2017/4/15 14:13:25","totalmoney":"400.00","prestepid":"","prestepuser":"[用户：]","nextstepid":"2270","nextstepuser":"[用户：系统管理员,林松]rn[部门：财务部]","nextsteplocation":"Center","billmemo":"1","bdeptid":"行政部","bcdefine1":"","bcdefine2":"","bcdefine3":"","bcdefine4":"","bcdefine5":"","bbilldate":"2017-04-15","lastapp_date":"2017-04-15 17:35:46","lastapp_uid":"67","attstatus":"0","savesourcetype":"PC"}]
         */

        private int total;
        private List<DataBean> data;

        @Override
        public String toString() {
            return "MsgBean{" +
                    "total=" + total +
                    ", data=" + data.toString() +
                    '}';
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * companyid :
             * flowid : 72
             * flowcode : submit
             * waiteid : 692
             * billid : 176
             * billno : PTBX1706050008
             * accobjname : 系统管理员
             * acctypename : 用户
             * submituser : 系统管理员
             * submituid : 1
             * submitdate : 2017-07-24
             * stepid : 2267
             * stepname : 部门经理
             * stepmemo :
             * steplocation : Center
             * dynamicid : 21403
             * returnrule :
             * showurl : ../CostReimb/130102_NormalAFR.aspx?pid=130102&billhelper=1
             * flowstatus : 已提交
             * programname : 普通费用报销
             * programid : 130102
             * newguid : 20170724151621033
             * buid : 系统管理员
             * bcreate_uid : 系统管理员
             * bcreate_date : 2017/6/5 15:15:22
             * totalmoney : 10.00
             * prestepid : 2410
             * prestepuser : [用户：]
             * nextstepid : 2270
             * nextstepuser : [用户：系统管理员,系统管理员]rn[部门：财务部]
             * nextsteplocation : Center
             * billmemo : cs
             * bdeptid : 人事部
             * bcdefine1 :
             * bcdefine2 :
             * bcdefine3 :
             * bcdefine4 :
             * bcdefine5 :
             * bbilldate : 2017-06-05
             * lastapp_date : 2017-07-24 15:16:21
             * lastapp_uid : 1
             * attstatus : 0
             * savesourcetype : PC
             */

            private String companyid;
            private String flowid;
            private String flowcode;
            private String waiteid;
            private String billid;
            private String billno;
            private String accobjname;
            private String acctypename;
            private String submituser;
            private String submituid;
            private String submitdate;
            private String stepid;
            private String stepname;
            private String stepmemo;
            private String steplocation;
            private String dynamicid;
            private String returnrule;
            private String showurl;
            private String flowstatus;
            private String programname;
            private String programid;
            private String newguid;
            private String buid;
            private String bcreate_uid;
            private String bcreate_date;
            private String totalmoney;
            private String prestepid;
            private String prestepuser;
            private String nextstepid;
            private String nextstepuser;
            private String nextsteplocation;
            private String billmemo;
            private String bdeptid;
            private String bcdefine1;
            private String bcdefine2;
            private String bcdefine3;
            private String bcdefine4;
            private String bcdefine5;
            private String bbilldate;
            private String lastapp_date;
            private String lastapp_uid;
            private String attstatus;
            private String savesourcetype;

            @Override
            public String toString() {
                return "DataBean{" +
                        "companyid='" + companyid + '\'' +
                        ", flowid='" + flowid + '\'' +
                        ", flowcode='" + flowcode + '\'' +
                        ", waiteid='" + waiteid + '\'' +
                        ", billid='" + billid + '\'' +
                        ", billno='" + billno + '\'' +
                        ", accobjname='" + accobjname + '\'' +
                        ", acctypename='" + acctypename + '\'' +
                        ", submituser='" + submituser + '\'' +
                        ", submituid='" + submituid + '\'' +
                        ", submitdate='" + submitdate + '\'' +
                        ", stepid='" + stepid + '\'' +
                        ", stepname='" + stepname + '\'' +
                        ", stepmemo='" + stepmemo + '\'' +
                        ", steplocation='" + steplocation + '\'' +
                        ", dynamicid='" + dynamicid + '\'' +
                        ", returnrule='" + returnrule + '\'' +
                        ", showurl='" + showurl + '\'' +
                        ", flowstatus='" + flowstatus + '\'' +
                        ", programname='" + programname + '\'' +
                        ", programid='" + programid + '\'' +
                        ", newguid='" + newguid + '\'' +
                        ", buid='" + buid + '\'' +
                        ", bcreate_uid='" + bcreate_uid + '\'' +
                        ", bcreate_date='" + bcreate_date + '\'' +
                        ", totalmoney='" + totalmoney + '\'' +
                        ", prestepid='" + prestepid + '\'' +
                        ", prestepuser='" + prestepuser + '\'' +
                        ", nextstepid='" + nextstepid + '\'' +
                        ", nextstepuser='" + nextstepuser + '\'' +
                        ", nextsteplocation='" + nextsteplocation + '\'' +
                        ", billmemo='" + billmemo + '\'' +
                        ", bdeptid='" + bdeptid + '\'' +
                        ", bcdefine1='" + bcdefine1 + '\'' +
                        ", bcdefine2='" + bcdefine2 + '\'' +
                        ", bcdefine3='" + bcdefine3 + '\'' +
                        ", bcdefine4='" + bcdefine4 + '\'' +
                        ", bcdefine5='" + bcdefine5 + '\'' +
                        ", bbilldate='" + bbilldate + '\'' +
                        ", lastapp_date='" + lastapp_date + '\'' +
                        ", lastapp_uid='" + lastapp_uid + '\'' +
                        ", attstatus='" + attstatus + '\'' +
                        ", savesourcetype='" + savesourcetype + '\'' +
                        '}';
            }

            public String getCompanyid() {
                return companyid;
            }

            public void setCompanyid(String companyid) {
                this.companyid = companyid;
            }

            public String getFlowid() {
                return flowid;
            }

            public void setFlowid(String flowid) {
                this.flowid = flowid;
            }

            public String getFlowcode() {
                return flowcode;
            }

            public void setFlowcode(String flowcode) {
                this.flowcode = flowcode;
            }

            public String getWaiteid() {
                return waiteid;
            }

            public void setWaiteid(String waiteid) {
                this.waiteid = waiteid;
            }

            public String getBillid() {
                return billid;
            }

            public void setBillid(String billid) {
                this.billid = billid;
            }

            public String getBillno() {
                return billno;
            }

            public void setBillno(String billno) {
                this.billno = billno;
            }

            public String getAccobjname() {
                return accobjname;
            }

            public void setAccobjname(String accobjname) {
                this.accobjname = accobjname;
            }

            public String getAcctypename() {
                return acctypename;
            }

            public void setAcctypename(String acctypename) {
                this.acctypename = acctypename;
            }

            public String getSubmituser() {
                return submituser;
            }

            public void setSubmituser(String submituser) {
                this.submituser = submituser;
            }

            public String getSubmituid() {
                return submituid;
            }

            public void setSubmituid(String submituid) {
                this.submituid = submituid;
            }

            public String getSubmitdate() {
                return submitdate;
            }

            public void setSubmitdate(String submitdate) {
                this.submitdate = submitdate;
            }

            public String getStepid() {
                return stepid;
            }

            public void setStepid(String stepid) {
                this.stepid = stepid;
            }

            public String getStepname() {
                return stepname;
            }

            public void setStepname(String stepname) {
                this.stepname = stepname;
            }

            public String getStepmemo() {
                return stepmemo;
            }

            public void setStepmemo(String stepmemo) {
                this.stepmemo = stepmemo;
            }

            public String getSteplocation() {
                return steplocation;
            }

            public void setSteplocation(String steplocation) {
                this.steplocation = steplocation;
            }

            public String getDynamicid() {
                return dynamicid;
            }

            public void setDynamicid(String dynamicid) {
                this.dynamicid = dynamicid;
            }

            public String getReturnrule() {
                return returnrule;
            }

            public void setReturnrule(String returnrule) {
                this.returnrule = returnrule;
            }

            public String getShowurl() {
                return showurl;
            }

            public void setShowurl(String showurl) {
                this.showurl = showurl;
            }

            public String getFlowstatus() {
                return flowstatus;
            }

            public void setFlowstatus(String flowstatus) {
                this.flowstatus = flowstatus;
            }

            public String getProgramname() {
                return programname;
            }

            public void setProgramname(String programname) {
                this.programname = programname;
            }

            public String getProgramid() {
                return programid;
            }

            public void setProgramid(String programid) {
                this.programid = programid;
            }

            public String getNewguid() {
                return newguid;
            }

            public void setNewguid(String newguid) {
                this.newguid = newguid;
            }

            public String getBuid() {
                return buid;
            }

            public void setBuid(String buid) {
                this.buid = buid;
            }

            public String getBcreate_uid() {
                return bcreate_uid;
            }

            public void setBcreate_uid(String bcreate_uid) {
                this.bcreate_uid = bcreate_uid;
            }

            public String getBcreate_date() {
                return bcreate_date;
            }

            public void setBcreate_date(String bcreate_date) {
                this.bcreate_date = bcreate_date;
            }

            public String getTotalmoney() {
                return totalmoney;
            }

            public void setTotalmoney(String totalmoney) {
                this.totalmoney = totalmoney;
            }

            public String getPrestepid() {
                return prestepid;
            }

            public void setPrestepid(String prestepid) {
                this.prestepid = prestepid;
            }

            public String getPrestepuser() {
                return prestepuser;
            }

            public void setPrestepuser(String prestepuser) {
                this.prestepuser = prestepuser;
            }

            public String getNextstepid() {
                return nextstepid;
            }

            public void setNextstepid(String nextstepid) {
                this.nextstepid = nextstepid;
            }

            public String getNextstepuser() {
                return nextstepuser;
            }

            public void setNextstepuser(String nextstepuser) {
                this.nextstepuser = nextstepuser;
            }

            public String getNextsteplocation() {
                return nextsteplocation;
            }

            public void setNextsteplocation(String nextsteplocation) {
                this.nextsteplocation = nextsteplocation;
            }

            public String getBillmemo() {
                return billmemo;
            }

            public void setBillmemo(String billmemo) {
                this.billmemo = billmemo;
            }

            public String getBdeptid() {
                return bdeptid;
            }

            public void setBdeptid(String bdeptid) {
                this.bdeptid = bdeptid;
            }

            public String getBcdefine1() {
                return bcdefine1;
            }

            public void setBcdefine1(String bcdefine1) {
                this.bcdefine1 = bcdefine1;
            }

            public String getBcdefine2() {
                return bcdefine2;
            }

            public void setBcdefine2(String bcdefine2) {
                this.bcdefine2 = bcdefine2;
            }

            public String getBcdefine3() {
                return bcdefine3;
            }

            public void setBcdefine3(String bcdefine3) {
                this.bcdefine3 = bcdefine3;
            }

            public String getBcdefine4() {
                return bcdefine4;
            }

            public void setBcdefine4(String bcdefine4) {
                this.bcdefine4 = bcdefine4;
            }

            public String getBcdefine5() {
                return bcdefine5;
            }

            public void setBcdefine5(String bcdefine5) {
                this.bcdefine5 = bcdefine5;
            }

            public String getBbilldate() {
                return bbilldate;
            }

            public void setBbilldate(String bbilldate) {
                this.bbilldate = bbilldate;
            }

            public String getLastapp_date() {
                return lastapp_date;
            }

            public void setLastapp_date(String lastapp_date) {
                this.lastapp_date = lastapp_date;
            }

            public String getLastapp_uid() {
                return lastapp_uid;
            }

            public void setLastapp_uid(String lastapp_uid) {
                this.lastapp_uid = lastapp_uid;
            }

            public String getAttstatus() {
                return attstatus;
            }

            public void setAttstatus(String attstatus) {
                this.attstatus = attstatus;
            }

            public String getSavesourcetype() {
                return savesourcetype;
            }

            public void setSavesourcetype(String savesourcetype) {
                this.savesourcetype = savesourcetype;
            }
        }
    }
}
