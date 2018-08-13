package professor;

public class PaperInfoEnum {
	//表Id
	String id;
	//专家Id
	String proID;
	//scholarid
	String scholarId;
	//期刊
	float JournalArticle;
	//会议
	float MeetingArticle;
	//专著
	float treatise;
	//其他
	float other;
	//总计
	int total;
	//姓名
	String proName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProID() {
		return proID;
	}
	public void setProID(String proID) {
		this.proID = proID;
	}
	public String getScholarId() {
		return scholarId;
	}
	public void setScholarId(String scholarId) {
		this.scholarId = scholarId;
	}
	public float getJournalArticle() {
		return JournalArticle;
	}
	public void setJournalArticle(float journalArticle) {
		JournalArticle = journalArticle;
	}
	public float getMeetingArticle() {
		return MeetingArticle;
	}
	public void setMeetingArticle(float meetingArticle) {
		MeetingArticle = meetingArticle;
	}
	public float getTreatise() {
		return treatise;
	}
	public void setTreatise(float treatise) {
		this.treatise = treatise;
	}
	public float getOther() {
		return other;
	}
	public void setOther(float other) {
		this.other = other;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
}
