package com.way.common.cache;

import java.util.List;

	/**
	 * Cluster properties.
	 */
	public class Cluster {
 
		/**
		 * Comma-separated list of "host:port" pairs to bootstrap from. This represents
		 * an "initial" list of cluster nodes and is required to have at least one
		 * entry.
		 */
		private List<String> nodes;
 
		/**
		 * Maximum number of redirects to follow when executing commands across the
		 * cluster.
		 */
		private Integer maxRedirects;
 
		public List<String> getNodes() {
			return this.nodes;
		}
 
		public void setNodes(List<String> nodes) {
			this.nodes = nodes;
		}
 
		public Integer getMaxRedirects() {
			return this.maxRedirects;
		}
 
		public void setMaxRedirects(Integer maxRedirects) {
			this.maxRedirects = maxRedirects;
		}
 
}
