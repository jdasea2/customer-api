package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.*;
import org.apache.solr.common.*;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import com.example.domain.Customer;
import com.example.domain.CustomerListing;
import com.example.domain.Status;
import java.util.logging.Logger;

@RestController
@RequestMapping("customer")
public class HelloController {

	private static final Logger log = Logger.getLogger(HelloController.class.getName());

	/**
	 * add a document to the index representing a customer. Customer IDs are
	 * presumed to be created externally.
	 */
	@PostMapping("/create")
	public Status create(@RequestBody Customer newCustomer) {

		try {
			SolrClient client = getClient();
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", newCustomer.getCustomerInfo().getId());
			document.addField("name", newCustomer.getCustomerInfo().getName());
			document.addField("zip", newCustomer.getZipCode());
			document.addField("address", newCustomer.getAddress());
			UpdateResponse response = client.add(document);
			client.commit();
			return new Status(new StringBuilder().append("SUCCESS: added customer ")
					.append(newCustomer.getCustomerInfo().getId()).toString());
		} catch (Exception ex) {
			log.info("Error running solr query: " + ex.getMessage());
			return new Status("Error adding new customer ");
		}
	}

	/**
	 * retrieve the complete document representing a customer by customer ID
	 */
	@GetMapping("/view/{customerId}")
	public Customer view(@PathVariable String customerId) {
		SolrQuery query = new SolrQuery();
		query.set("q", "customerId:" + customerId);
		QueryResponse response = getResponse(query);
		Customer customer = extractCustomer(response);
		return customer;
	}

	/**
	 * List a paginated view of customer IDs and names in lexicographical order by
	 * name. The API-user will need to return the cursorID of the last call to see
	 * the next page.
	 */
	@GetMapping("/list/{cursorId}")
	public CustomerListing list(@PathVariable String cursorId) {
		String cursorMark = cursorId != null ? cursorId : "*";
		String queryString = "q=id:*&sort=name desc,id asc&fl=id,name&rows=20&cursorMark=" + cursorId;
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		QueryResponse response = getResponse(query);
		return extractCustomerListing(response);
	}

	/**
	 * Delete a customer by ID
	 */
	@GetMapping("/delete/{cursorId}")
	public Status delete(@PathVariable long customerId) {
		try {
			SolrClient client = getClient();
			client.deleteByQuery("id:" + customerId);
			client.commit();
			return new Status("SUCCESS: deleted customer " + customerId);
		} catch (Exception ex) {
			log.info("Error running solr query: " + ex.getMessage());
			return new Status("Error deleting customer " + customerId);
		}
	}

	private QueryResponse getResponse(SolrQuery query) {
		try {
			SolrClient solr = getClient();
			QueryResponse response = solr.query(query);
			return response;
		} catch (Exception ex) {
			log.info("Error running solr query: " + ex.getMessage());
			return null;
		}
	}

	private SolrClient getClient() {
		return new CloudSolrClient.Builder().withZkHost(defineZookeeperHost()).build();
	}

	protected String defineZookeeperHost() {
		return "serverOne:2181,serverTwo:2181,serverThree:2181";
	}

	/**
	 * extracts a customer listing from a QueryResponse
	 */
	private CustomerListing extractCustomerListing(QueryResponse response) {
		return null;
	}

	/**
	 * extracts a customer listing from a QueryResponse
	 */
	private Customer extractCustomer(QueryResponse response) {
		return null;
	}

}
