package com.jobportal.serviceimpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jobportal.service.GeneralService;

@Service
public class GeneralServiceImpl implements GeneralService {

	@Override
	public String getAllSchemes() {
		RestTemplate restTemplate = new RestTemplate();

//		String url = "https://d4u.godrejproperties.com/fast-brokerage/get-all-schemes";

		String apiUrl = "https://d4u.godrejproperties.com/fast-brokerage-uat/get-all-schemes";

		// Set the headers for the request

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"eyJraWQiOiJ0PitsOlklcHVXfm9HbDsiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJnb2RyZWoiLCJleHAiOjM3OTc1MzQ5MDM0LCJhdWQiOiJBTEwiLCJncm91cHMiOlsiUm9sZUR0byBbaWQ9MSwgcm9sZU5hbWU9QWRtaW4sIG1vZHVsZXM9W01vZHVsZUR0byBbaWQ9MSwgbW9kdWxlTmFtZT1NQU5BR0VfVVNFUl0sIE1vZHVsZUR0byBbaWQ9MiwgbW9kdWxlTmFtZT1DUkVBVEVfU0NIRU1FXSwgTW9kdWxlRHRvIFtpZD0zLCBtb2R1bGVOYW1lPUVESVRfU0NIRU1FXSwgTW9kdWxlRHRvIFtpZD00LCBtb2R1bGVOYW1lPVZJRVdfU0NIRU1FXSwgTW9kdWxlRHRvIFtpZD01LCBtb2R1bGVOYW1lPVZJRVdfQk9PS0lOR10sIE1vZHVsZUR0byBbaWQ9NiwgbW9kdWxlTmFtZT1NQU5BR0VfUk9MRV0sIE1vZHVsZUR0byBbaWQ9NywgbW9kdWxlTmFtZT1FRElUX1VTRVJdLCBNb2R1bGVEdG8gW2lkPTgsIG1vZHVsZU5hbWU9RURJVF9ST0xFXSwgTW9kdWxlRHRvIFtpZD05LCBtb2R1bGVOYW1lPVZJRVdfTUlMRVNUT05FXSwgTW9kdWxlRHRvIFtpZD0xMCwgbW9kdWxlTmFtZT1DUkVBVEVfTUlMRVNUT05FXSwgTW9kdWxlRHRvIFtpZD0xMSwgbW9kdWxlTmFtZT1ET1dOTE9BRF9SRVBPUlRdLCBNb2R1bGVEdG8gW2lkPTEyLCBtb2R1bGVOYW1lPUFVVEhPUl9TQ0hFTUVdXSwgaXNBY3RpdmU9WSwgY3JlYXRlZERhdGU9MjAyMi0wMi0yNCAwMDowMDowMC4wLCBjcmVhdGVkQnk9YWRtaW4sIG1vZGlmaWVkQnk9YWRtaW4sIG1vZGlmaWVkRGF0ZT0yMDIyLTA1LTMwIDIyOjU1OjI3LjMwNF0iXSwianRpIjoiVG12cFJNNXV4alA5SjE2RmJMYWFkQSIsImlhdCI6MTY4NzM0OTAzNCwic3ViIjoiQVVUSFRPS0VOIiwidXNlcklkIjoiMSJ9.cJLsFoLqFngVR6sffprDNu7bTWoYN025vGUaqZtQeuqKqNLxHXgxOtIU1t1GDXKK8Wf5SJea9fFdJzPIJyUIA0oXCHGuor4DewhvdmTVm8bG1hKhr5zl7T-IFqpQvUSD6M7x02G6C8D8MqO3K0E0GtJzFXx3KHUE2bHyhr040brg_S1avBnF8R9VVbvYsifkWhsfi_QT3PFMn4JS3VnL6UYpzZay7ZktTqhdo824ajGiY8ut8FkSSya7w8ZLMr5EdWuHm0yzzx1jZMv_2KowlSqo4p7ld01z_eJsJwqitM2V8WxiQU4nKeayLRtCBPy9ZufCQNv3i50pZGdCpyR5RQ");
		// Add other headers as needed
//		headers.add(apiUrl, apiUrl);
		// Build the request entity with headers
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);
		// Process the response as needed
		String responseBody = response.getBody();
		return responseBody;
	}
}
