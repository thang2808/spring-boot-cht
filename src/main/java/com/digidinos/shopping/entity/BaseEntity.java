	package com.digidinos.shopping.entity;
	
	import java.io.Serializable;
	import java.util.Date;
	
	import javax.persistence.Column;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
	import javax.persistence.TemporalType;
	
	@MappedSuperclass
	public class BaseEntity implements Serializable{
		private static final long serialVersionUID = 1842915944086730414L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", nullable = false, updatable = false)
		protected Integer id;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "CREATED_AT", nullable = false)
		protected Date createdAt;
		
		@Column(name = "UPDATED_AT")
		protected Date updatedAt;
		
		@Column(name = "DELETED_AT")
		protected Date deletedAt;
		
		@Column(name = "IS_DELETE", nullable = false)
		private boolean isDeleted;
	
		
		
		public Integer getId() {
			return id;
		}
	
		public void setId(Integer string) {
			this.id = string;
		}
	
		public Date getCreatedAt() {
			return createdAt;
		}
	
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
	
		public Date getUpdatedAt() {
			return updatedAt;
		}
	
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
	
		public Date getDeletedAt() {
			return deletedAt;
		}
	
		public void setDeletedAt(Date deletedAt) {
			this.deletedAt = deletedAt;
		}
	
		public boolean isDeleted() {
			return isDeleted;
		}
	
		public void setDeleted(boolean isDeleted) {
			this.isDeleted = isDeleted;
		}
	
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	
		public BaseEntity(Integer id, Date createdAt, Date updatedAt, Date deletedAt, boolean isDeleted) {
	        this.id = id;
	        this.createdAt = createdAt;
	        this.updatedAt = updatedAt;
	        this.deletedAt = deletedAt;
	        this.setDeleted(isDeleted); // Corrected here
	    }
		
		public BaseEntity() {
			
		}
	}
