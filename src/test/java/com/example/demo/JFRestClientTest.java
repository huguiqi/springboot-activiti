package com.example.demo;

import com.example.demo.common.restClient.RestClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by guiqi on 2017/8/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-test.xml" })
@WebAppConfiguration
@Ignore
public class JFRestClientTest {

    @Autowired
    private RestClient restClient;

    @Autowired
    MockHttpSession session;

    @Before
    public void setUp(){
        session.setAttribute("TokenId","VIANu_09zaV7RvBr6RwXgoKhjZWw22vuIC4yIYwJYvhycZqA3_HzXn56hKPuqvvNSAZIWrHQo7UYOTP309NDilSflcFVyDUPmv2I-GS-LEg");
    }


    @Test
    @Ignore
    public void testPostForAddAppraise(){

//        AppraiseVO dto =  new AppraiseVO();
//        dto.setRecord(new EmpRecordVO(null,23,new Date(),12,12,12,"fdsfsdf", AppraiseStatus.CHECKER_VIEW,null,null,null));
//        List<EmpStageVO> stageParamList = new ArrayList<EmpStageVO>();
//        List<EmpBehaviourVO> behaviourParamList = new ArrayList<EmpBehaviourVO>();
//        for (int i=0;i<10;i++){
//            EmpStageVO stageParam = new EmpStageVO(null,null, TimeBlockType.ELEVEN_CLOCK,"plan","llllllkdfjd","llllllkdfjd","llllllkdfjd","llllllkdfjd","jfjfjddd");
//            EmpBehaviourVO behaviourParam = new EmpBehaviourVO(null,null, BehaviorType.COMMUNICATE,"fdsflkjjkkjk","llllllkdfjd","llllllkdfjd");
//            stageParamList.add(stageParam);
//            behaviourParamList.add(behaviourParam);
//        }
//
//        dto.setStageList(stageParamList);
//        dto.setBehaviourList(behaviourParamList);
//
//        BaseResponse response = (BaseResponse) restClient.post(CommonConstant.KEY_APPRAISE_ADD,dto);
//
//        Assert.isTrue(response.getCode().equals("200000"),"返回成功");

    }



    @Test
//    @Ignore
    public void testPostForModify(){

//        AppraiseVO dto =  new AppraiseVO();
//        dto.setRecord(new EmpRecordVO(1,23,new Date(),12,12,12,"fdsfsdf",AppraiseStatus.CHECKER_VIEW,null,null,null));
//        List<EmpStageVO> stageParamList = new ArrayList<EmpStageVO>();
//        List<EmpBehaviourVO> behaviourParamList = new ArrayList<EmpBehaviourVO>();
//        for (int i=1;i<11;i++){
//            EmpStageVO stageParam = new EmpStageVO(i,null, TimeBlockType.TEN_CLOCK,"plan"+i,"llllllkdfjd","llllllkdfjd","llllllkdfjd","llllllkdfjd","jfjfjddd");
//            EmpBehaviourVO behaviourParam = new EmpBehaviourVO(i,null,BehaviorType.DEDICATED,"behaviour"+i,"llllllkdfjd","llllllkdfjd");
//            stageParamList.add(stageParam);
//            behaviourParamList.add(behaviourParam);
//        }
//
//        dto.setStageList(stageParamList);
//        dto.setBehaviourList(behaviourParamList);
//
//        BaseResponse response = (BaseResponse) restClient.patch(CommonConstant.KEY_APPRAISE_MODIFY,dto);
//
//        Assert.isTrue(response.getCode().equals("200000"),"返回成功");

    }


    @Test
    public void testQuery(){

//        QueryRecord queryRecord = new QueryRecord();
//        queryRecord.setUserId("24");
//        BaseResponse response = (BaseResponse)restClient.get(CommonConstant.KEY_APPARISE_QUERY,queryRecord);
//        if (response.getCode().equals("200000")){
//            List<EmpRecordVO> list =(List<EmpRecordVO>) restClient.toResultBean(response.getData(),new TypeReference<ArrayList<EmpRecordVO>>(){});
//            Assert.notNull(list.get(0).getRecordId(),"record没查到");
//        }else
//            Assert.notNull(response.getMessage(),"response message not null");
//        System.out.println("jjjjjjjjjjjjj");
    }
}
