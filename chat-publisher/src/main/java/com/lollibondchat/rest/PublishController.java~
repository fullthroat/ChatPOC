package com.lollibondchat.rest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lollibondchat.data.entity.Message;
import com.lollibondchat.publisher.ChatMessagePublisher;

@RestController
@RequestMapping("/company")
public class PublishController {

//	@Autowired
//	private CompanyService companyService;
	@Autowired
	private ChatMessagePublisher chatMessagePublisher;

/*	@RequestMapping
	public Callable<List<Company>> listAllCompanies() {
		return new Callable<List<Company>>() {
			@Override
			public List<Company> call() throws Exception {
				return companyService.findAll();
			}
		};

	}
*/
/*	@RequestMapping("/{id}")
	public String fetchCompany(@RequestParam(value = "id", defaultValue = "1") String id) {
		Long idl = null;

		idl = Long.parseLong(id);

		Company company = companyService.searchById(idl);
		return company.toString();
	}*/

	@RequestMapping(value="/publish",method = RequestMethod.POST)
	public CompletableFuture<Message> publisChatMessage(@RequestBody Message mesg) throws JsonProcessingException {
		Message msg = new Message();
		String message=mesg.getMessage();
		String userId=mesg.getTopic();
		CompletableFuture<Message> cf = CompletableFuture.supplyAsync(new Supplier<Message>() {
			@Override
			public Message get() {
				if (chatMessagePublisher.publishChatMessage(message, userId)) {
					msg.setMessage(message);
					msg.setTopic(userId);
					return msg;
				} else {
					msg.setMessage("Message not published to kafka");
					msg.setTopic(userId);
				}
				System.out.println("not saving returning");
				return msg;
			}
		});
		return cf;
	}

}
