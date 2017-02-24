/**
 * 
 */
package root;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author Marius
 *
 */
public class MainClass {
	public static int numOfVideos;
	static int endpointsSize;
	static long requests;
	static int cacheServ;
	static int capacServ;
	static PriorityQueue<Video> pQueueVideos;
	public static CacheServer[] cacheServers;
	
	static Endpoint[] endpoints;
	static Video[] videos;
	

	public static void main(String[] args) {
		pQueueVideos = new PriorityQueue<>(10, new VideoComparator());
		String fileName = "C:/Users/Marius/Desktop/input.txt";
		String outFileName = "C:/Users/Marius/Desktop/output.txt";
		
		
		try (Scanner reader = new Scanner(new InputStreamReader(new FileInputStream(fileName)));
		        PrintStream str = new PrintStream(outFileName)) {
			numOfVideos = reader.nextInt();
			endpointsSize = reader.nextInt();
			requests = reader.nextInt();
			cacheServ = reader.nextInt();
			capacServ = reader.nextInt();
			videos = new Video[numOfVideos];
			cacheServers = new CacheServer[cacheServ];
			
			for ( int i = 0; i < numOfVideos; i++ ) {
				int size = reader.nextInt();
				videos[i] = new Video(i, size);
			}
			for ( int i = 0; i < cacheServ; i++ ) {
				cacheServers[i] = new CacheServer(i, capacServ);
			}
			
			endpoints = new Endpoint[endpointsSize];
			int cacheIndex;

			for ( int i = 0; i < endpoints.length; i++ ) {
				endpoints[i] = new Endpoint(reader.nextInt(), cacheServ, numOfVideos);
				cacheIndex = reader.nextInt();
				
				for ( int j = 0; j < cacheIndex; j++ ) {
					int cacheServerIndex = reader.nextInt();
					int value=reader.nextInt();
					endpoints[i].cacheDelay[cacheServerIndex] = value;
					
				}
				
			}
			
			for ( int i = 0; i < requests; i++ ) {
				int videoIndex = reader.nextInt();
				int endpointIndex = reader.nextInt();
				int noRequests = reader.nextInt();
				
				endpoints[endpointIndex].videoRequest[videoIndex] = noRequests;
				
				videos[videoIndex].totalRequests += noRequests;
				if ( videos[videoIndex].requestsToN_point < noRequests ) {
					videos[videoIndex].requestsToN_point = noRequests;
					videos[videoIndex].endpointWithMostRequests = endpointIndex;
				}
				
			}
			
			pQueueVideos.addAll(Arrays.asList(videos));
			
			addToCacheServers();
			
			int cacheServerCound = 0;
			
			System.out.println(Arrays.asList(cacheServers));
			for ( CacheServer s : cacheServers ) {
				cacheServerCound += (s.sizeAvailable == s.totalSize ? 0 : 1);
			}
			
			str.println(cacheServerCound);
			for(CacheServer s: cacheServers)
			{
				if(s.totalSize!=s.sizeAvailable)
				{
					str.print(s.Id + " ");
					for ( Video v : s.storedVideos )//// check spaces
					{
						str.print(v.id);
						str.print(" ");/////
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		



	}
	
	static void addToCacheServers() {
		
		while (!pQueueVideos.isEmpty()) {
			Video video = pQueueVideos.remove();
			int bestServer = endpoints[video.endpointWithMostRequests].getBestCache(video.size);
			
			if ( bestServer != -1 ) {
				cacheServers[bestServer].setSizeAvailable(video.size);
				cacheServers[bestServer].addVideo(video);
			}
			
		}
		
	}
	

	static class VideoComparator implements Comparator<Video> {
		
		@Override
		public int compare(Video o1, Video o2) {
			int val = 0;
			
			if ( o1.requestsToN_point > o2.requestsToN_point ) {
				val = -1;
				
			} else if ( o1.requestsToN_point < o2.requestsToN_point ) {
				val = 1;
			}
			
			return val;
		}
		
	}
	
}
