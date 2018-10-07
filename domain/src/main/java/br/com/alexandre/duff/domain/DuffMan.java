package br.com.alexandre.duff.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DuffMan {
	public static class Opinion implements Serializable {
		
		private static final long serialVersionUID = 7326192552429572999L;
		
		private List<Item> items = new ArrayList<Item>();

		public Opinion() { }

		public Opinion(final List<Item> items) {
			super();
			this.items = items;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		public static class Item implements Serializable {

			private static final long serialVersionUID = -2849816192091462431L;

			private String beerStyle;		
			private List<Playlist> playlist;

			public Item() { }

			public Item(final String beerStyle, final List<Playlist> playList) {
				this.beerStyle = beerStyle;
				this.playlist = playList;
			}

			public String getBeerStyle() {
				return beerStyle;
			}

			public void setBeerStyle(String beerStyle) {
				this.beerStyle = beerStyle;
			}

			public List<Playlist> getPlaylist() {
				return playlist;
			}

			public void setPlaylist(List<Playlist> playlist) {
				this.playlist = playlist;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((beerStyle == null) ? 0 : beerStyle.hashCode());
				result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Item other = (Item) obj;
				if (beerStyle == null) {
					if (other.beerStyle != null)
						return false;
				} else if (!beerStyle.equals(other.beerStyle))
					return false;
				if (playlist == null) {
					if (other.playlist != null)
						return false;
				} else if (!playlist.equals(other.playlist))
					return false;
				return true;
			}

			@Override
			public String toString() {
				return beerStyle;
			}

		}
	}

	public static class Classification implements Serializable {
		
		private static final long serialVersionUID = 1986831943868317531L;
		
		private String beerStyle;
		private Integer ranking;
		
		public Classification() { }

		public String getBeerStyle() {
			return beerStyle;
		}

		public void setBeerStyle(String beerStyle) {
			this.beerStyle = beerStyle;
		}

		public Integer getRanking() {
			return ranking;
		}

		public void setRanking(Integer ranking) {
			this.ranking = ranking;
		}		
	}
}
