package com.gsugambit.partydjserver.model;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsugambit.partydjserver.utils.ObjectUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "queue_item")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
public class QueueItem {
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Station station;
	
	@Id
	@GenericGenerator(name = "uuid-generator", 
	strategy = "com.gsugambit.partydjserver.utils.UUIDGenerator")
	@GeneratedValue(generator = "uuid-generator")
	@Column(name = "queue_item_id")
	private String id;
	
	@Column(nullable = false, name = "queue_item_index")
	private Long index;
	
	@Column(name = "youtube_url")
	private String url;
	
	@Column(nullable = false)
	@Getter(AccessLevel.NONE)
	private Boolean played;
	
	@Column(nullable = false)
	private String title;
	
	@Column(name="something_else")
	private String user;
	
	public Boolean isPlayed() {
		return played;
	}
	
	public static final Comparator<QueueItem> QUEUE_ITEM_COMPARATOR = new Comparator<>() {
		public int compare(QueueItem thisQ, QueueItem thatQ) {
			return thisQ.getIndex().compareTo(thatQ.getIndex());
		}
	};
	
	@Override
	public String toString() {
		return ObjectUtils.toString(this);
	}
}
