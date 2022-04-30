package aws.example.s3.create;

import java.io.IOException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;

public class S3BucketCreation {

	public static void main(String[] args) throws IOException {

		Region region = Region.US_EAST_1;
		S3Client s3Client = S3Client.builder().region(region).build();

		String bucketName = "test-from-sdk-bucket-" + System.currentTimeMillis();
		String objectName = "test-obj-from-sdk";

		createBucket(s3Client, bucketName, region);

		System.out.println("Uploading object...");

		createObject(s3Client, bucketName, objectName);

		System.out.println("Upload complete");
		System.out.printf("%n");

		// cleanUp(s3, bucket, key);

		System.out.println("Closing the connection to {S3}");
		
		s3Client.close();
		
		System.out.println("Connection closed");
		System.out.println("Exiting...");
	}

	private static void createObject(S3Client s3, String bucketName, String objectName) {
		// pass bucket and object name in PutObjectRequest
		PutObjectRequest putObjRequest = PutObjectRequest.builder().bucket(bucketName).key(objectName).build();
		
		// Add object(file content)
		RequestBody objectContent = RequestBody.fromString("Testing request from Java SDK client.");
		
		s3.putObject(putObjRequest, objectContent);
	}

	public static void createBucket(S3Client s3Client, String bucketName, Region region) {
		try {
			// If the bucket is getting created in US_EAST_1 (N. Virginia), there is no need to pass region name.
			s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName)
					.createBucketConfiguration(CreateBucketConfiguration.builder().build()).build());
			
			System.out.println("Creating bucket: " + bucketName);
			
			s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder().bucket(bucketName).build());
			
			System.out.println(bucketName + " is ready.");
			System.out.printf("%n");
		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
	}

	public static void cleanUp(S3Client s3Client, String bucketName, String keyName) {
		System.out.println("Cleaning up...");
		try {
			System.out.println("Deleting object: " + keyName);
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(keyName)
					.build();
			s3Client.deleteObject(deleteObjectRequest);
			System.out.println(keyName + " has been deleted.");
			System.out.println("Deleting bucket: " + bucketName);
			DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
			s3Client.deleteBucket(deleteBucketRequest);
			System.out.println(bucketName + " has been deleted.");
			System.out.printf("%n");
		} catch (S3Exception e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		System.out.println("Cleanup complete");
		System.out.printf("%n");
	}
}
