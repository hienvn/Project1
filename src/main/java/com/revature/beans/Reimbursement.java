package com.revature.beans;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class Reimbursement {

	private long r_id;
	private double r_amount;
	private String r_description;
	private byte[] r_receipt_byte;
	//private Blob r_receipt_blob;
	private String r_receipt_string;
	private Timestamp r_submitted;
	private Timestamp r_resolved;
	private long uid_author;
	private long uid_resolver;
	private long r_type_id;
	private long r_status_id;
	private String r_type;
	private String r_status;

	public Reimbursement(long r_amount, String r_description, byte[] r_receipt_byte, long uid_author, long r_type_id) {
		super();
		// this.r_id = r_id;
		this.r_amount = r_amount;
		this.r_description = r_description;
		this.r_receipt_byte = r_receipt_byte;
		this.r_submitted = new Timestamp((System.currentTimeMillis() / 1000) * 1000);
		// this.r_resolved = new Timestamp(System.currentTimeMillis());
		this.uid_author = uid_author;
		// this.uid_resolver = 1005;
		this.r_type_id = r_type_id;
		this.r_status_id = 1;
		// this.r_type = r_type;
		// this.r_status = r_status;
	}

	public Reimbursement(long r_id, long uid_resolver, long r_status_id) {
		super();
		this.r_id = r_id;
		this.r_resolved = new Timestamp(System.currentTimeMillis() / 1000 * 1000);
		this.uid_resolver = uid_resolver;
		this.r_status_id = r_status_id;
	}

	public Reimbursement(long r_id, double r_amount, String r_description, byte[] r_receipt_byte, Timestamp r_submitted,
			Timestamp r_resolved, long uid_author, long uid_resolver, long r_type_id, long r_status_id, String r_type,
			String r_status) {
		super();
		this.r_id = r_id;
		this.r_amount = r_amount;
		this.r_description = r_description;
		this.r_receipt_byte = r_receipt_byte;
		this.r_submitted = r_submitted;
		this.r_resolved = r_resolved;
		this.uid_author = uid_author;
		this.uid_resolver = uid_resolver;
		this.r_type_id = r_type_id;
		this.r_status_id = r_status_id;
		this.r_type = r_type;
		this.r_status = r_status;
		this.r_receipt_string = new String(Base64.getEncoder().encode(r_receipt_byte));
	}

	public long getR_id() {
		return r_id;
	}

	public void setR_id(long r_id) {
		this.r_id = r_id;
	}

	public double getR_amount() {
		return r_amount;
	}
	public String getR_amount_money() {
		return NumberFormat.getCurrencyInstance().format(r_amount);
	}

	public void setR_amount(double r_amount) {
		this.r_amount = r_amount;
	}

	public String getR_description() {
		return r_description;
	}

	public void setR_description(String r_description) {
		this.r_description = r_description;
	}

	public byte[] getR_receipt_byte() {
		return r_receipt_byte;
	}

	public void setR_receipt_byte(byte[] r_receipt_byte) {
		this.r_receipt_byte = r_receipt_byte;
	}

//	public Blob getR_receipt_blob() {
//		return r_receipt_blob;
//	}
//
//	public void setR_receipt_blob(Blob r_receipt_blob) {
//		this.r_receipt_blob = r_receipt_blob;
//	}

	public String getR_receipt_string() {
		return this.r_receipt_string;
	}

	public void setR_receipt_string(String r_receipt_string) {
		this.r_receipt_string = r_receipt_string;
	}

	public String getR_submitted_string() {
		if(r_submitted != null)
			return new SimpleDateFormat("dd/MM/yyyy\nHH:mm").format(r_submitted);
		else
			return "";
	}
	public Timestamp getR_submitted() {
		return r_submitted;
	}

	public void setR_submitted(Timestamp r_submitted) {
		this.r_submitted = r_submitted;
	}

	public String getR_resolved_string() {
		if(r_resolved != null)
			return new SimpleDateFormat("dd/MM/yyyy\nHH:mm").format(r_resolved);
		else
			return "";
	}

	public Timestamp getR_resolved() {
		return r_resolved;
	}

	public void setR_resolved(Timestamp r_resolved) {
		this.r_resolved = r_resolved;
	}

	public long getUid_author() {
		return uid_author;
	}

	public void setUid_author(long uid_author) {
		this.uid_author = uid_author;
	}

	public long getUid_resolver() {
		return uid_resolver;
	}

	public void setUid_resolver(long uid_resolver) {
		this.uid_resolver = uid_resolver;
	}

	public long getR_type_id() {
		return r_type_id;
	}

	public void setR_type_id(long r_type_id) {
		this.r_type_id = r_type_id;
	}

	public long getR_status_id() {
		return r_status_id;
	}

	public void setR_status_id(long r_status_id) {
		this.r_status_id = r_status_id;
	}

	public String getR_type() {
		return r_type;
	}

	public void setR_type(String r_type) {
		this.r_type = r_type;
	}

	public String getR_status() {
		return r_status;
	}

	public void setR_status(String r_status) {
		this.r_status = r_status;
	}

	@Override
	public String toString() {
		return "Reimbursement [r_id=" + r_id + ", r_amount=" + r_amount + ", r_description=" + r_description
				+ ", r_receipt=" + r_receipt_string + ", r_submitted=" + r_submitted + ", r_resolved=" + r_resolved
				+ ", uid_author=" + uid_author + ", uid__resolver=" + uid_resolver + ", r_type_id=" + r_type_id
				+ ", r_status_id=" + r_status_id + ", r_type=" + r_type + ", r_status=" + r_status + "]";
	}
}
