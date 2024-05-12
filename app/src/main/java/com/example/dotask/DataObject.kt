package com.example.dotask

object DataObject {
    var listdata= mutableListOf<TaskInfo>()

    fun setData(title:String,priority:String,description:String){
        listdata.add(TaskInfo(title,priority,description))
    }

    fun getAllData():List<TaskInfo>{
        return listdata
    }

    fun getData(pos:Int): TaskInfo {
        return listdata[pos]
    }

    fun deleteData(pos:Int){
        listdata.removeAt(pos)
    }

    fun updateData(pos:Int,title:String,priority:String,description:String){
        listdata[pos].title=title
        listdata[pos].priority=priority
        listdata[pos].description=description
    }
}