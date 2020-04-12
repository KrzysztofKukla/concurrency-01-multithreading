package pl.kukla.krzys.concurrency.multithreading;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Krzysztof Kukla
 */
//in  the first case we will load very large book
// client will provide word to find how many appear in the book 'localhost/search?work=talk'
public class ExecutorsWithFixedNumberThreadsCores {

    private static final String INPUT_FILE = "/war_and_peace.txt";
    //    private static final String INPUT_FILE = "/test.txt";
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws IOException, URISyntaxException {
        byte[] bytes = loadBook(INPUT_FILE);
        String text = new String(bytes);
        serverStart(text);
    }

    private static byte[] loadBook(String file) throws URISyntaxException, IOException {
        Path path = Paths.get(ExecutorsWithFixedNumberThreadsCores.class.getResource(file).toURI());
        return Files.readAllBytes(path);
    }

    private static void serverStart(String text) throws IOException {
        //second parameters means size of the queue for Http server request
        //zero means all request should end up in thread pool's queue instead
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/search", new WordCountHandler(text));

        //this Executor schedule ( planuje ) each incoming request to pool of worker threads
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        server.setExecutor(executor);
        server.start();
    }

    //this class handles each incoming Http request and send Http response
    private static class WordCountHandler implements HttpHandler {

        //it stores book content
        private String text;

        private WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];
            if (!action.equals("word")) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }
            long count = countWord(word);

            byte[] response = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream responseBody = httpExchange.getResponseBody();
            //write responseBody to Http response stream
            responseBody.write(response);

            //close stream and send to client upstream
            responseBody.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;

            while (index >= 0) {
                index = text.indexOf(word, index);

                //if index is positive than we found word in text
                if (index >= 0) {
                    count++;

                    //starting from the next position where we found word last time
                    index++;
                }
            }
            return count;
        }

    }

}
