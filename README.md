## Aplicação simples com crud usuário e reuniões

- Java 11
- Maven
- Spring Boot
- H2 bd
- Lombok
- Link app: http://localhost:8080/mymeetings/
- Link h2 : http://localhost:8080/mymeetings/h2-console/

## Teste de Integração:
- SpringBootTest - carrega todo contexto em memoria

## Teste Unitário:
- [Mockito](https://site.mockito.org/) - instancia classes e controla o comportamento dos métodos

## Biblioteca:
- [ModelMapper](http://modelmapper.org/) - mapeamento de objetos 

## [Hateoas](https://spring.io/projects/spring-hateoas)
- HiperLinks = representação de recursos com links
	-> Recurso tem que ter o link de referência a ele mesmo (withSelRel()) e aos outros recursos disponiveis em links (withRel()).
```sh
	EntityModel<MeetingResponse> meetingModel = EntityModel.of(meetingResponse, 
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MeetingController.class).oneMeeting(id)).withSelfRel(),
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MeetingController.class).allMeetings(new HashMap<>())).withRel("meetings"),
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MeetingCategoryController.class).OneMeetingCategory(meetingResponse.getMeetingCategoryId())).withRel("meetingCategory"),
		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).OneUser(meetingResponse.getUserId())).withRel("user")
	);
```
