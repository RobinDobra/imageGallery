package com.example.blog

import com.example.blog.utility.WebscraperTagsFromPicJumbo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class ImagegalleryApplication

fun main(args: Array<String>) {
	runApplication<ImagegalleryApplication>(*args)


	val webscraperTagsFromPicJumbo = WebscraperTagsFromPicJumbo()
	webscraperTagsFromPicJumbo.init();
//
//	val region: Region = Region.EU_CENTRAL_1
//	val s3Client: S3Client = S3Client.builder().region(region).build()
//
//	val bucketName = "myfirsts3bucketrd"
//	val key = "tagsWithoutDuplicates.txt"
//
//	listAllFromBucket(s3Client, bucketName, region)
//	getObject(s3Client, bucketName, key)
}

//fun listAllFromBucket(s3Client: S3Client, bucketName: String, region: Region) {
//	val listBucketsRequest = ListBucketsRequest.builder().build()
//	val listBucketsResponse: ListBucketsResponse = s3Client.listBuckets(listBucketsRequest)
//	listBucketsResponse.buckets().stream().forEach { x: Bucket ->
//		println(
//			x.name()
//		)
//	}
//}
//
//fun getObject(s3Client: S3Client, bucketName: String, key: String) {
//	val getObjectRequest = GetObjectRequest.builder()
//		.bucket(bucketName)
//		.key(key)
//		.build()
//
//	val responseInput = s3Client.getObject(getObjectRequest)
////	println(responseInput.reader().readLines())
//	File("testfile.txt").writeBytes(responseInput.readBytes())
//	File("testfile.txt").forEachLine { println(it) }
//}
//
//	val bucket = "bucket" + System.currentTimeMillis()
//	val key = "key"
//
//	tutorialSetup(s3, bucket, region)
//
//	println("Uploading object...")
//
//	s3.putObject(
//		PutObjectRequest.builder().bucket(bucket).key(key)
//			.build(),
//		RequestBody.fromString("Testing with the {sdk-java}")
//	)
//
//	println("Upload complete")
//	System.out.printf("%n")
//
////	cleanUp(s3, bucket, key)
//
//	println("Closing the connection to {S3}")
//	s3.close()
//	println("Connection closed")
//	println("Exiting...")
//
//}
//
//fun tutorialSetup(s3Client: S3Client, bucketName: String, region: Region) {
//	try {
//		s3Client.createBucket(
//			CreateBucketRequest
//				.builder()
//				.bucket(bucketName)
//				.createBucketConfiguration(
//					CreateBucketConfiguration.builder()
//						.locationConstraint(region.id())
//						.build()
//				)
//				.build()
//		)
//		println("Creating bucket: $bucketName")
//		s3Client.waiter().waitUntilBucketExists(
//			HeadBucketRequest.builder()
//				.bucket(bucketName)
//				.build()
//		)
//		println("$bucketName is ready.")
//		System.out.printf("%n")
//	} catch (e: S3Exception) {
//		System.err.println(e.awsErrorDetails().errorMessage())
//		System.exit(1)
//	}
//}
//
//fun cleanUp(s3Client: S3Client, bucketName: String, keyName: String) {
//	println("Cleaning up...")
//	try {
//		println("Deleting object: $keyName")
//		val deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(keyName).build()
//		s3Client.deleteObject(deleteObjectRequest)
//		println("$keyName has been deleted.")
//		println("Deleting bucket: $bucketName")
//		val deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build()
//		s3Client.deleteBucket(deleteBucketRequest)
//		println("$bucketName has been deleted.")
//		System.out.printf("%n")
//	} catch (e: S3Exception) {
//		System.err.println(e.awsErrorDetails().errorMessage())
//		System.exit(1)
//	}
//	println("Cleanup complete")
//	System.out.printf("%n")
//}
