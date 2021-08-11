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

        val awsCreds = AwsBasicCredentials.create(
//            "AKIA4KVEKI7PBO4XCXH2",
//            "4hGcA4eEmA/IBLaCZWJPtTNVeJrPun5GT2A2bxPv"
            "AKIA4KVEKI7PEHYPLFQ6",
            "67sx5jNkEOYAWU/GLqbAdn+6e6V0WsCwvOwD5j7T"
        )

        val region: Region = Region.EU_CENTRAL_1
//        val s3Client: S3Client = AmazonS3
        return S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()

    }
}
