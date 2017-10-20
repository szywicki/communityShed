package com.libertymutual.goforcode.communityShed.config;

import java.net.URL;
import java.sql.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.communityShed.models.ConfirmedUser;
import com.libertymutual.goforcode.communityShed.models.Group;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.models.User;
import com.libertymutual.goforcode.communityShed.repositories.GroupRepo;
import com.libertymutual.goforcode.communityShed.repositories.RequestRepo;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;
import com.libertymutual.goforcode.communityShed.repositories.UserRepo;
import com.libertymutual.goforcode.communityShed.repositories.ConfirmedUserRepo;


@Configuration

public class SeedData {

	public SeedData(ToolRepo toolRepo, UserRepo userRepo, GroupRepo groupRepo, RequestRepo requestRepo, PasswordEncoder encoder) {
	
		Group group1 = new Group ("eighties", "The Eighties Group");
		Group group2 = new Group ("sixties", "The Sixties Group");
		
		ConfirmedUser user1 = new ConfirmedUser(encoder.encode("test"), "user@domain.com", "User", "U");
		userRepo.save(user1);
		ConfirmedUser user2 = new ConfirmedUser(encoder.encode("test"), "bob@ross.com", "Bob", "Ross");
		userRepo.save(user2);
		ConfirmedUser user3 = new ConfirmedUser(encoder.encode("test"), "lonely@user.com", "Lonely", "User");
		userRepo.save(user3);
		
		group1.addUserToGroup(user1);
		group2.addUserToGroup(user1);
		group2.addUserToGroup(user2);
		
		
		new Tool("Hammer Drill", "Bosh 13.5 Amp Corded 1-78 in. Rotary Hammer Drill", "powertool", "Bosh", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Bosh+13.5+Amp+Corded+1-78+in.+Rotary+Hammer+Drill.jpg", user1);	
		toolRepo.save(new Tool("Flashlight", "Coast HP7 Focusing LED Flashlight", "handtool", "Coast", null, null, "Available", 4, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Focusing+LED+Flashlight.jpg", user1));

		groupRepo.save(group1);
		toolRepo.save(new Tool("Drill/Driver", "Dewalt 20-Volt Lithium Cordless Drill-Driver", "powertool", "Brand1", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+20-Volt+Lithium+Cordless+Drill-Driver.jpg", user2));

		groupRepo.save(group2);
		
//		toolRepo.save(new Tool("123456789012345678901234567890", "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "123456789012345678901234567890", "123456789012345678901234567890", null, null, "123456789012345678901234567890", 1, null, user1));
		//                    (String toolName, String toolDescription, String category, String manufacturer, Date dateCheckout, Date dateReturn, String status, int toolAge, URL image, ConfirmedUser owner)
		toolRepo.save(new Tool("Lantern", "Eveready Readyflex Floating Lantern", "handtool", "Eveready", null, null, "Available", 4, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Eveready+Readyflex+Floating+Lantern.jpg", user1));
		toolRepo.save(new Tool("Flashlight", "Coast HP7 Focusing LED Flashlight", "handtool", "Coast", null, null, "Available", 4, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Focusing+LED+Flashlight.jpg", user1));	
		toolRepo.save(new Tool("Welder", "Lincoln Electric 180 Amp Weld-Pak MIG Wire Feed Welder", "weldingtool", "Lincoln Electric", null, null, "Available", 3, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Lincoln+Electric+180+Amp+Weld-Pak+MIG+Wire+Feed+Welder.jpg", user2));
		toolRepo.save(new Tool("Soldering Iron", "Weller 40-Watt LED Soldering Iron", "weldingtool", "Weller", null, null, "Available", 3, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Weller+40-Watt+LED+Soldering+Iron.jpg", user2));
		toolRepo.save(new Tool("Circular Saw", "Makita 15 Amp 7-1/4 in. Corded Circular Saw", "powertool", "Makita", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Makita+15+Amp+7-14+in.+Corded+Circular+Saw.jpg", user2));
		toolRepo.save(new Tool("Reciprocating Saw", "Milwaukee 15 Amp Super Reciprocating Saw", "powertool", "Milwaukee", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Milwaukee+15+Amp+Super+Reciprocating+Saw.jpg", user2));
		toolRepo.save(new Tool("Drill/Driver", "Dewalt 20-Volt Lithium Cordless Drill-Driver", "powertool", "Brand1", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+20-Volt+Lithium+Cordless+Drill-Driver.jpg", user2));
		toolRepo.save(new Tool("Hammer Drill", "Bosh 13.5 Amp Corded 1-78 in. Rotary Hammer Drill", "powertool", "Bosh", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Bosh+13.5+Amp+Corded+1-78+in.+Rotary+Hammer+Drill.jpg", user2));
		toolRepo.save(new Tool("Spray Gun", "Husky Gravity Feed HVLP Spray Gun", "powertool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Gravity+Feed+HVLP+Spray+Gun.jpg", user2));		
		toolRepo.save(new Tool("Mechanics Tool Set", "Mechanics Tool Set (108-Piece)", "handtool", "Dewalt", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+Mechanics+Tool+Set+(108-Piece).jpg", user2));		toolRepo.save(new Tool("Wood Chisel", "Economy Wood Chisel Set(3-Piece)", "handtool", "Buck Bros.", null, null, "Available", 1, null, user2));
		toolRepo.save(new Tool("Adjustable Wrench", "Husky Double-Speed Adjustable Wrench Set (3-Piece)", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Double-Speed+Adjustable+Wrench+Set+(3-Piece).jpg", user2));
		toolRepo.save(new Tool("Stubby Set", "Husky Stubby Set With Extendable Ratchet(46-Piece)", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Stubby+Set+With+Extendable+Ratchet(46-Piece).jpg", user2));
		toolRepo.save(new Tool("Screwdriver", "Screwdriver Set (6-Piece)", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Variety+Screwdriver+Set+(6-Piece).jpg", user2));
		toolRepo.save(new Tool("SAE Ratcheting Wrench", "SAE Ratcheting Combination Wrench Set(10-Piece)", "handtool", "SAE", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/SAE+Ratcheting+Combination+Wrench+Set+(10-Piece).jpg", user2));
		toolRepo.save(new Tool("Tongue Plier", "Channellock 12in. Tongue and Grove Pliers", "handtool", "Channellock", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Channellock+12in.+Tongue+and+Grove+Pliers.jpg", user2));
		toolRepo.save(new Tool("Cutting Plier", "Husky 7 in. Diagonal Cutting Pliers", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+7+in.+Diagonal+Cutting+Pliers.jpg", user2));
		toolRepo.save(new Tool("Hammer", "Estwing 20 oz. Straight-Claw Rip Hammer", "handtool", "Estwing", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Estwing+20+oz.+Straight-Claw+Rip+Hammer.jpg", user2));		
		toolRepo.save(new Tool("Floor Jack", "Husky 3-Ton Low Profile Floor Jack With Speedy Lift", "automotivetools", "Husky", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+3-Ton+Low+Profile+Floor+Jack+With+Speedy+Lift.jpg", user2));
		toolRepo.save(new Tool("Rolling Seat", "Wen 250 lb.Capacity Rolling Mechanic Seat With Onboard Storage", "automotivetools", "WEN", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Wen+250+lb.Capacity+Rolling+Mechanic+Seat+With+Onboard+Storage.jpg", user2));
		toolRepo.save(new Tool("Steel Jack", "Big Red 6-Ton Double Lock Steel Jack", "automotivetools", "Big Red", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Big+Red+6-Ton+Double+Lock+Steel+Jack.jpg", user2));
		toolRepo.save(new Tool("Scissor Jack", "Big Red 1.5-Ton Scissor Jack", "automotivetools", "Big Red", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Big+Red+1.5-Ton+Scissor+Jack.jpg", user2));
		toolRepo.save(new Tool("Jum Starter", "Dewalt 1400 Peak Amp Starter with Digital Compressor", "automotivetools", "Dewalt", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+1400+Peak+Amp+Starter+with+Digital+Compressor.jpg", user2));
		toolRepo.save(new Tool("Jumper Calbles", "Formey 12 Ft Heavy Duty Battery Jumper Cables", "automotivetools", "Formey", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Formey+12+Ft+Heavy+Duty+Battery+Jumper+Cables.jpg", user2));
		toolRepo.save(new Tool("123456789012345678901234567890", "12345678901234567890123456789012345678901234567890123456789012345678901234567890", "123456789012345678901234567890", "123456789012345678901234567890", null, null, "123456789012345678901234567890", 1, null, user2));
		toolRepo.save(new Tool("Circular Saw", "Makita 15 Amp 7-1/4 in. Corded Circular Saw", "powertool", "Makita", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Makita+15+Amp+7-14+in.+Corded+Circular+Saw.jpg", user2));
		toolRepo.save(new Tool("Reciprocating Saw", "Milwaukee 15 Amp Super Reciprocating Saw", "powertool", "Milwaukee", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Milwaukee+15+Amp+Super+Reciprocating+Saw.jpg", user2));
		toolRepo.save(new Tool("Drill/Driver", "Dewalt 20-Volt Lithium Cordless Drill-Driver", "powertool", "Brand1", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+20-Volt+Lithium+Cordless+Drill-Driver.jpg", user2));
		toolRepo.save(new Tool("Hammer Drill", "Bosh 13.5 Amp Corded 1-78 in. Rotary Hammer Drill", "powertool", "Bosh", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Bosh+13.5+Amp+Corded+1-78+in.+Rotary+Hammer+Drill.jpg", user2));
		toolRepo.save(new Tool("Spray Gun", "Husky Gravity Feed HVLP Spray Gun", "powertool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Gravity+Feed+HVLP+Spray+Gun.jpg", user2));
		toolRepo.save(new Tool("Mechanics Tool Set", "Mechanics Tool Set (108-Piece)", "handtool", "Dewalt", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+Mechanics+Tool+Set+(108-Piece).jpg", user2));
		toolRepo.save(new Tool("Wood Chisel", "Economy Wood Chisel Set(3-Piece)", "handtool", "Buck Bros.", null, null, "Available", 1, null, user2));
		toolRepo.save(new Tool("Adjustable Wrench", "Husky Double-Speed Adjustable Wrench Set (3-Piece)", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Double-Speed+Adjustable+Wrench+Set+(3-Piece).jpg", user2));
		toolRepo.save(new Tool("Stubby Set", "Husky Stubby Set With Extendable Ratchet(46-Piece)", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+Stubby+Set+With+Extendable+Ratchet(46-Piece).jpg", user2));
		toolRepo.save(new Tool("Screwdriver", "Screwdriver Set (6-Piece)", "handtool", "Husky", null, null, "Available", 1, null, user2));
		toolRepo.save(new Tool("SAE Ratcheting Wrench", "SAE Ratcheting Combination Wrench Set(10-Piece)", "handtool", "SAE", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/SAE+Ratcheting+Combination+Wrench+Set+(10-Piece).jpg", user2));
		toolRepo.save(new Tool("Tongue Plier", "Channellock 12in. Tongue and Grove Pliers", "handtool", "Channellock", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Channellock+12in.+Tongue+and+Grove+Pliers.jpg", user2));
		toolRepo.save(new Tool("Cutting Plier", "Husky 7 in. Diagonal Cutting Pliers", "handtool", "Husky", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+7+in.+Diagonal+Cutting+Pliers.jpg", user2));
		toolRepo.save(new Tool("Hammer", "Estwing 20 oz. Straight-Claw Rip Hammer", "handtool", "Estwing", null, null, "Available", 1, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Estwing+20+oz.+Straight-Claw+Rip+Hammer.jpg", user2));
		toolRepo.save(new Tool("Floor Jack", "Husky 3-Ton Low Profile Floor Jack With Speedy Lift", "automotivetools", "Husky", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+3-Ton+Low+Profile+Floor+Jack+With+Speedy+Lift.jpg", user2));
		toolRepo.save(new Tool("Rolling Seat", "Wen 250 lb.Capacity Rolling Mechanic Seat With Onboard Storage", "automotivetools", "WEN", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Wen+250+lb.Capacity+Rolling+Mechanic+Seat+With+Onboard+Storage.jpg", user2));
		toolRepo.save(new Tool("Steel Jack", "Big Red 6-Ton Double Lock Steel Jack", "automotivetools", "Big Red", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Big+Red+6-Ton+Double+Lock+Steel+Jack.jpg", user2));
		toolRepo.save(new Tool("Scissor Jack", "Big Red 1.5-Ton Scissor Jack", "automotivetools", "Big Red", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Big+Red+1.5-Ton+Scissor+Jack.jpg", user2));
		toolRepo.save(new Tool("Jum Starter", "Dewalt 1400 Peak Amp Starter with Digital Compressor", "automotivetools", "Dewalt", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Dewalt+1400+Peak+Amp+Starter+with+Digital+Compressor.jpg", user2));
		toolRepo.save(new Tool("Jumper Calbles", "Formey 12 Ft Heavy Duty Battery Jumper Cables", "automotivetools", "Formey", null, null, "Available", 2, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Formey+12+Ft+Heavy+Duty+Battery+Jumper+Cables.jpg", user2));
	
		toolRepo.save(new Tool("Welder", "Lincoln Electric 180 Amp Weld-Pak MIG Wire Feed Welder", "weldingtool", "Lincoln Electric", null, null, "Available", 3, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Lincoln+Electric+180+Amp+Weld-Pak+MIG+Wire+Feed+Welder.jpg", user2));
		toolRepo.save(new Tool("Soldering Iron", "Weller 40-Watt LED Soldering Iron", "weldingtool", "Weller", null, null, "Available", 3, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Weller+40-Watt+LED+Soldering+Iron.jpg", user2));
		toolRepo.save(new Tool("Torch Head", "Bernzomatic TS$000T Trigger Start Torch Head", "weldingtool", "Bernzomatic", null, null, "Available", 3, null ,user1));
		
		toolRepo.save(new Tool("Lantern", "Eveready Readyflex Floating Lantern", "handtool", "Eveready", null, null, "Available", 4, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Eveready+Readyflex+Floating+Lantern.jpg", user1));
		toolRepo.save(new Tool("Flashlight", "Coast HP7 Focusing LED Flashlight", "handtool", "Coast", null, null, "Available", 4, "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Coast+HP7+Focusing+LED+Flashlight.jpg", user1));
	}
}