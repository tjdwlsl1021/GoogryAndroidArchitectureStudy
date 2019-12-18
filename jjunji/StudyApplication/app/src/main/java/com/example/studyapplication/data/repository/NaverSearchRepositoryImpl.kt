package com.example.studyapplication.data.repository

import com.example.studyapplication.data.datasource.remote.NaverRemoteDataSource
import com.example.studyapplication.network.Conn

class NaverSearchRepositoryImpl(private val remoteDataSource: NaverRemoteDataSource) : NaverSearchRepository {

    override fun getMovieList(query: String, conn: Conn) {
        remoteDataSource.getMovieList(query, conn)
    }

    override fun getBlogList(query: String, conn: Conn) {
        remoteDataSource.getBlogList(query, conn)
    }

}