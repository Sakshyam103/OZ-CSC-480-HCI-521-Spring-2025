package quotes;

import com.quotes.MongoUtil;
import com.quotes.QuoteObject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MongoUtilTesting {
    public static MongoUtil mongoUtil;
    public static QuoteObject quoteObject;
    ObjectId id;
    public static ArrayList<ObjectId> ids;

    @BeforeAll
    public static void setUp() {
        mongoUtil = new MongoUtil("mongodb+srv://database_admin:pXzO2cMkmk7LXVCH@csc480cluster.ldmco.mongodb.net/?retryWrites=true&w=majority");
        ids = new ArrayList<>();
    }

    @BeforeEach
    public void beforeEach() {
        ObjectId objectId = new ObjectId();
        ObjectId creatorId = new ObjectId();
        quoteObject = new QuoteObject();
        quoteObject.setId(objectId);
        quoteObject.setAuthor("author");
        quoteObject.setBookmarks(1);
        quoteObject.setShares(1);
        quoteObject.setTags(new ArrayList<>(Arrays.asList("tag1", "tag2")));
        quoteObject.setText("text");
        quoteObject.setCreator(creatorId);
        id = mongoUtil.createQuote(quoteObject);
        System.out.println(id);
        ids.add(id);
    }

    @AfterEach
    public void afterEach() {
        for (ObjectId id : ids) {
            mongoUtil.deleteQuote(id);
        }
    }

    @Test
    @Order(1)
    public void testCreateQuote() {
        assertEquals(5, ids.size());
    }


    @Test
    @Order(2)
    public void testCreateQuoteWithIdAndText(){
        quoteObject.setText("test");
        ids.add(mongoUtil.createQuote(quoteObject));
        assertEquals(8, ids.size());
    }


      //create Quote with empty text
    @Test
    @Order(3)
    public void testCreateQuoteWithEmptyText(){
        ObjectId objectId = new ObjectId();
        quoteObject.setId(objectId);
        quoteObject.setAuthor("author");
        quoteObject.setText("");
        quoteObject.setCreator(objectId);
        assertNull(mongoUtil.createQuote(quoteObject));
    }

    // just passing id and text


    //setting text null
    @Test
    @Order(4)
    public void testCreateQuoteWithTextNull(){
        quoteObject.setText(null);
        assertNull(mongoUtil.createQuote(quoteObject));
    }

    @Test
    @Order(5)
    public void testGetQuote(){
        assertNotNull(mongoUtil.getQuote(id));
    }


    //checking when the id is not present in the database
    @Test
    @Order(6)
    public void testGetQuoteswithRandomID(){
        assertNull(mongoUtil.getQuote(new ObjectId()));
    }

    //checking when the id is null
    @Test
    @Order(7)
    public void testGetQuoteswithNullID(){
        assertNull(mongoUtil.getQuote(null));
    }

    //checking when the creator is null
    //need to make sure the creator is not null
    @Test
    @Order(8)
    public void testGetQuoteswithNullCreator(){
        ObjectId objectId = new ObjectId();
        quoteObject = new QuoteObject();
        quoteObject.setId(objectId);
        quoteObject.setAuthor("author");
        quoteObject.setText("trial");
        quoteObject.setCreator(null);
        ObjectId id = mongoUtil.createQuote(quoteObject);
        assertNull(mongoUtil.getQuote(id));
    }





    //Testing parseQuote methods
    //method is private

     //test when we update quotes
    @Test
    @Order(9)
    public void testUpdateQuotes(){
        quoteObject.setText("this is a test");
        quoteObject.setAuthor("author2");
        assertTrue(mongoUtil.updateQuote(quoteObject));
    }


    //failed cause when getting quote object with null quoteObject
    @Test
    @Order(10)
    public void testUpdateQuotesWithNull(){
        quoteObject = null;
        assertFalse(mongoUtil.updateQuote(quoteObject));
    }



    //Failed because if getQuotes is null then parse method returns exception
    @Test
    @Order(11)
    public void testUpdateQuotesWithRandomId(){
        QuoteObject newQuoteObject = new QuoteObject();
        newQuoteObject.setId(new ObjectId());
        newQuoteObject.setAuthor("author3");
        assertFalse(mongoUtil.updateQuote(newQuoteObject));
    }



    //if id is null then getObject gives you null
    @Test
    @Order(12)
    public void testUpdateQuotesWithNullId(){
        quoteObject.setId(null);
        assertFalse(mongoUtil.updateQuote(quoteObject));
    }






    //the jwtString creates error
//    @Test
//    void testSearchWithoutFilters() {
//        String searchQuery = "test";
//        boolean filterUsed = false;
//        boolean filterBookmarked = false;
//        boolean filterUploaded = false;
//        String includeTerms = null;
//        String excludeTerms = null;
//        String jwtString = "jwtString";
//        String result = mongoUtil.searchQuote(searchQuery, filterUsed, filterBookmarked, filterUploaded, includeTerms, excludeTerms, jwtString);
//        assertNotNull(result);
//        assertTrue(result.contains("test"));  // Ensure the search term "test" is present in the result
//    }
//
//    @Test
//    void testSearchWithFuzzyQuery() {
//        String searchQuery = "tst";
//        boolean filterUsed = false;
//        boolean filterBookmarked = false;
//        boolean filterUploaded = false;
//        String includeTerms = null;
//        String excludeTerms = null;
//        String jwtString = "jwtString";
//        String result = mongoUtil.searchQuote(searchQuery, filterUsed, filterBookmarked, filterUploaded, includeTerms, excludeTerms, jwtString);
//        assertNotNull(result);
//        assertTrue(result.contains("test"));
//    }
//
//        // Test Case 3: Search with an empty query but getting error because while creating jwt from string gets error
//    @Test
//    void testSearchWithEmptyQuery() {
//        String searchQuery = "";
//        boolean filterUsed = false;
//        boolean filterBookmarked = false;
//        boolean filterUploaded = false;
//        String includeTerms = null;
//        String excludeTerms = null;
//        String jwtString = "jwtString";
//        String result = mongoUtil.searchQuote(searchQuery, filterUsed, filterBookmarked, filterUploaded, includeTerms, excludeTerms, jwtString);
//        assertNotNull(result);
//        assertTrue(result.isEmpty() || result.contains("no results"));  // Handle no results case
//    }








}
