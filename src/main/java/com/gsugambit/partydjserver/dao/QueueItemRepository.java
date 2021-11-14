package com.gsugambit.partydjserver.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsugambit.partydjserver.model.QueueItem;

public interface QueueItemRepository extends PagingAndSortingRepository<QueueItem, String>{

}
