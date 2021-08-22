package com.example.blog.configuration

import org.apache.catalina.connector.Connector
import org.apache.coyote.http2.Http2Protocol
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TomcatHTTP2Configuration {

    @Bean
    fun tomcatCustomizer(): ConfigurableServletWebServerFactory? {
        val factory = TomcatServletWebServerFactory()
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector -> connector.addUpgradeProtocol(Http2Protocol()) })
        return factory
    }
}
