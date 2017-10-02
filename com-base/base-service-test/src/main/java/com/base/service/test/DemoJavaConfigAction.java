/*
 * Copyright 2006-2014 handu.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.service.test;

import org.springframework.stereotype.Component;

/**
 * @author Jinkai.Ma
 */
@Component
public class DemoJavaConfigAction {
    
/*    @Reference
    private QueryService queryService;

    @PostConstruct
    public void start() throws Exception {
                
        System.out.println("------queryService start------");
        QueryObjectEventCondition cond = new QueryObjectEventCondition();
	   	 cond.setIndex("ce");
	   	 cond.setType("tb_object_event_master");
	   	 cond.setDistance(100);
	   	 cond.setLat(40.857271);
	   	 cond.setLng(111.715895);
	   	cond.setAObjId("16933918053961252");
	   	cond.setGroupBy("a_obj_id");
	   	 //cond.setAObjId("19713380100295459");
	   	 //cond.setBObjId("11606302101104334");
        //try {
        // System.out.println(queryService.queryObjectEvent(cond));
	   	System.out.println(queryService.groupByQuery(cond));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println("------queryService end------");
        System.out.println(System.getProperty("dubbo.application.logger"));
    }*/
}
