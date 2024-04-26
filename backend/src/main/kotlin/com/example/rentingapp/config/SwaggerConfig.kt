package com.example.rentingapp.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List

@Configuration
class SwaggerConfig {

    @Value("\${openapi.dev-url}")
    private val devUrl: String? = null

    @Value("\${openapi.prod-url}")
    private val prodUrl: String? = null

    @Bean
    fun myOpenAPI(): OpenAPI {
        val devServer = Server()
        devServer.setUrl(devUrl)
        devServer.setDescription("Project IADI 2023: Renting Application")
        val prodServer = Server()
        prodServer.setUrl(prodUrl)
        prodServer.setDescription("Project IADI 2023: Renting Application")
        val contact = Contact()
        contact.setEmail("k.trytek@campus.fct.unl.pt")
        contact.setName("Karol Trytek 68876, Breixo Senra Pastoriza 68835, Julia Cwynar 68846")
        contact.setUrl("https://www.fct.unl.pt")
        val mitLicense: License = License().name("MIT License").url("https://choosealicense.com/licenses/mit/")
        val info: Info = Info()
            .title(" Project IAID: Renting Application")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints for Project IAID: Renting Application.")
            .termsOfService("https://www.https://github.com/costaseco/iadi2023/blob/main/project.md")
            .license(mitLicense)
        return OpenAPI().info(info).servers(List.of(devServer, prodServer))
    }

}