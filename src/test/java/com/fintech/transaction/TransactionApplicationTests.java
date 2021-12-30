package com.fintech.transaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TransactionApplicationTests {

	@Test
	public void getTransaction() {
		String endpoint = "http://localhost:8090/fintech/transaction/getTransaction/{ref}";
		var response =
				given()
						.pathParam("ref", 763654)
						.when()
						.get(endpoint)
						.then()
						.assertThat()
						.statusCode(200);

	}

}
