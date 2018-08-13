package professor;

public class BaseInfoEnum {
	//表ID
	String id;
	//专家ID
	String proID;
	//专家姓名
	String ProName;
	//百度文库上的scholarid
	String ScholarId;
	//被引用次数
	int quoted;
	//成果数
	int papers;
	//H指数
	int HIndex;
	//G指数
	int GIndex;
	//领域
	String filed;
	//看过次数
	int volum;
	//照片
	String image;
	
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
	public String getProName() {
		return ProName;
	}
	public void setProName(String proName) {
		ProName = proName;
	}
	public String getScholarId() {
		return ScholarId;
	}
	public void setScholarId(String scholarId) {
		ScholarId = scholarId;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public int getVolum() {
		return volum;
	}
	public void setVolum(int volum) {
		this.volum = volum;
	}
	public int getQuoted() {
		return quoted;
	}
	public void setQuoted(int quoted) {
		this.quoted = quoted;
	}
	public int getPapers() {
		return papers;
	}
	public void setPapers(int papers) {
		this.papers = papers;
	}
	public int getHIndex() {
		return HIndex;
	}
	public void setHIndex(int hIndex) {
		HIndex = hIndex;
	}
	public int getGIndex() {
		return GIndex;
	}
	public void setGIndex(int gIndex) {
		GIndex = gIndex;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
