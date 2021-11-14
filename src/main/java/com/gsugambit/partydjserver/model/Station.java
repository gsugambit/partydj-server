package com.gsugambit.partydjserver.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsugambit.partydjserver.utils.ObjectUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "station")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
public class Station {

	@Id
	@GenericGenerator(name = "uuid-generator", 
	strategy = "com.gsugambit.partydjserver.utils.UUIDGenerator")
	@GeneratedValue(generator = "uuid-generator")
	@Column(name = "station_id")
	private String id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@GeneratedValue(generator = "uuid-generator")
	@Column(nullable = false)
	private String url;

	@Builder.Default

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="station_queue_item",
			joinColumns = @JoinColumn(name = "station_id"), 
			inverseJoinColumns = @JoinColumn(name="queue_item_id"))
	private List<QueueItem> queue = new ArrayList<>();

	public void setQueue(List<QueueItem> queue) {
		if (queue == null) {
			this.queue.clear();
		} else {
			this.queue = queue;
		}
	}

	@Override
	public String toString() {
		return ObjectUtils.toString(this);
	}
}
