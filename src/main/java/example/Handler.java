package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

// Handler value: example.Handler
public class Handler implements RequestHandler<Map<String,String>, String>{
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  static final String MOBY_DICK_URL = "https://www.gutenberg.org/files/2701/2701-0.txt";
  @Override
  public String handleRequest(Map<String,String> event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    // log execution details
    logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
    logger.log("CONTEXT: " + gson.toJson(context));
    // process event
    logger.log("EVENT: " + gson.toJson(event));
    logger.log("EVENT TYPE: " + event.getClass());

    String writtenWord = "";
    try {
      BufferedInputStream in = new BufferedInputStream(new URL(MOBY_DICK_URL).openStream());

      byte dataBuffer[] = new byte[1024];
      int bytesRead = 0;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
          writtenWord += new String(dataBuffer, 0, bytesRead);
        }
    } catch(IOException ioe) {
      return "500 " + ioe.getMessage();
    }
    /*
     * TODO: in real life would use Dependency Inversion Principle of depending on an abstract WordFrequencyReader  here
     *  we can take this pattern one further by additionally segregating an abstraction/interface for WordFrequencyStreamReader
     *  which would extend initial functionality based on shape of data coming in
     *
     */
    WordFrequencyStreamReader topFiftyMostFrequentWordsReader = new WordFrequencyStreamReader(writtenWord, 50);
    String response = Arrays.toString(topFiftyMostFrequentWordsReader.getTopKFrequentWordsUnordered());
    return response;
  }
}