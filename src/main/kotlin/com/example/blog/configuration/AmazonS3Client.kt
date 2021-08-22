package com.example.blog.configuration

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client


@Component
class AmazonS3Client {

    @Bean
    fun getS3Client(): S3Client {

        // Amazon S3 Read Only User
        val awsCreds = AwsBasicCredentials.create(
            "AKIA4KVEKI7PNPFILLO4",
            "/qwvd4orNLtgGTroRhbpTIwavX+g9vrD3ElrreTf"
        )

        val region: Region = Region.EU_CENTRAL_1
        return S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()

    }
}
