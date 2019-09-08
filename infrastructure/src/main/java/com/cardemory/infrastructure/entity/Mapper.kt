package com.cardemory.infrastructure.entity

/**
 * Must be named in following order of models:
 * data -> domain -> ui
 * I.e. ProfileDbToDomainMapper, not ProfileDomainToDbMapper
 */
interface Mapper<FROM, TO> {
    fun from(item: FROM): TO {
        TODO("Implement in child class")
    }

    fun to(item: TO): FROM {
        TODO("Implement in child class")
    }
}