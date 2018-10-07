package br.com.alexandre.duff.spotify.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaylistResponse implements Serializable {
	
	private static final long serialVersionUID = -5830405375145508320L;
	
	private Playlists playlists;
	
	public PlaylistResponse() { }
	
	public Playlists getPlaylists() {
		return playlists;
	}

	public void setPlaylists(Playlists playlists) {
		this.playlists = playlists;
	}

	public static class Playlists implements Serializable {
		
		private static final long serialVersionUID = 8846910949780996205L;
		
		private List<Item> items = new ArrayList<>();
		
		public Playlists() { }
		
		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		public static class Item implements Serializable {
			
			private static final long serialVersionUID = -1915882522052627369L;
			
			private String name;
			private Tracks tracks;
			
			public Item() { }
			
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public Tracks getTracks() {
				return tracks;
			}

			public void setTracks(Tracks tracks) {
				this.tracks = tracks;
			}

			public static class Tracks implements Serializable {
				
				private static final long serialVersionUID = 32760336441077900L;
				private String href;
				
				public Tracks() { }

				public String getHref() {
					return href;
				}

				public void setHref(String href) {
					this.href = href;
				}
			}
		}
	}
}
