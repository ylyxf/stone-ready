package client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class OkHttpLoggingInterceptor implements Interceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpLoggingInterceptor.class);

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		System.out.println("hello--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
		// before , request.body()
		try {
			Response response = chain.proceed(request);
			// after
			return response;
		} catch (Exception e) {
			// log error
			throw e;
		} finally {
			// clean up
		}
	}

}