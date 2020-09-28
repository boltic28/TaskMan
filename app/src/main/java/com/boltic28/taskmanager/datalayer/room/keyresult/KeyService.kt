package com.boltic28.taskmanager.datalayer.room.keyresult

import com.boltic28.taskmanager.datalayer.room.DBService
import io.reactivex.Single

class KeyService: DBService<KeyEntity, KeyEntity> {
    override fun create(item: KeyEntity): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun readAll(): Single<List<KeyEntity>> {
        TODO("Not yet implemented")
    }

    override fun readById(id: Long): Single<KeyEntity> {
        TODO("Not yet implemented")
    }

    override fun update(item: KeyEntity): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: KeyEntity): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun toEntity(item: KeyEntity): KeyEntity {
        TODO("Not yet implemented")
    }

    override fun fromEntity(entity: KeyEntity): KeyEntity {
        TODO("Not yet implemented")
    }
}