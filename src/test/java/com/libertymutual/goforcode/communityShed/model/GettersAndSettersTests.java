package com.libertymutual.goforcode.communityShed.model;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.InvitedUser;
import com.libertymutual.goforcode.communityShed.models.Request;
import com.libertymutual.goforcode.communityShed.models.SemanticsProduct;
import com.libertymutual.goforcode.communityShed.models.SimpleTool;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;

	public class GettersAndSettersTests {

		
		 @Test
		 public void testConfirmedUser() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(ConfirmedUser.class);
		 }
		 
		 @Test
		 public void testGroup() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(Group.class);
		 }
		 
		 @Test
		 public void testInvitedUser() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(InvitedUser.class);
		 }
		 
		 @Test
		 public void testRequest() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(Request.class);
		 }
		 
		 @Test
		 public void testSemanticsProduct() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(SemanticsProduct.class);
		 }
		 
		 @Test
		 public void testSimpleTool() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(SimpleTool.class);
		 }
		 
		 @Test
		 public void testTool() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(Tool.class);
		 }

		 @Test
		 public void testUser() {
		  BeanTester beanTester = new BeanTester();
		  beanTester.testBean(User.class);
		 }
		 
}
