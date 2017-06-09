package BackEnd;

public interface CommandsSQL {
	public boolean Connect(String username, String password, String db);
	public String[] SELECT(String select,String fromTable);
	public String[] SELECT(String select,String fromTable, String where);
	public String[] GETTABLESOFDATABASE();
	public String[][] SELECTENTIRETABLE();
	public String[][] SELECTENTIRETABLE(String WHERE);
}
