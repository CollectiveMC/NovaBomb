package org.cyberpwn.novabomb.util;

import java.util.List;
import org.cyberpwn.novabomb.util.DataCluster.ClusterType;

/**
 * @author cyberpwn
 */
public class ClusterStringList extends Cluster
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> strings;
	
	public ClusterStringList(List<String> value)
	{
		super(ClusterType.STRING_LIST, 0.0);
		strings = value;
	}
	
	public List<String> get()
	{
		return strings;
	}
	
	public void set(List<String> s)
	{
		value = 0.0;
		strings = s;
	}
}
