package br.com.alexandre.duff.spotify.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TracksResponse implements Serializable {

	private static final long serialVersionUID = 8913351443542671877L;

	private List<Item> items = new ArrayList<>();
	
	public TracksResponse() { }
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = 6647081643664058884L;

		private Track track;
		
		public Item() { }
		
		public Track getTrack() {
			return track;
		}

		public void setTrack(Track track) {
			this.track = track;
		}

		public static class Track implements Serializable {

			private static final long serialVersionUID = -8508238078142874966L;

			private String name;
			private ExternalUrls external_urls;
			private List<Artist> artists = new ArrayList<>();
			
			public Track() { }
			
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
			
			public ExternalUrls getExternal_urls() {
				return external_urls;
			}

			public void setExternal_urls(ExternalUrls external_urls) {
				this.external_urls = external_urls;
			}

			public List<Artist> getArtists() {
				return artists;
			}

			public void setArtists(List<Artist> artists) {
				this.artists = artists;
			}
			
			public static class ExternalUrls implements Serializable {

				private static final long serialVersionUID = 4754425545666376789L;
				
				private String spotify;
				
				public ExternalUrls() { }

				public String getSpotify() {
					return spotify;
				}

				public void setSpotify(String spotify) {
					this.spotify = spotify;
				}				
			}

			public static class Artist implements Serializable {

				private static final long serialVersionUID = -7611810106876423513L;

				private String name;
				
				public Artist() { }

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}				
			}
		}
	}
}
