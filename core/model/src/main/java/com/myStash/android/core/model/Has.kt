package com.myStash.android.core.model

import android.os.Parcelable
import com.myStash.android.common.util.isNotNull
import com.myStash.android.common.util.offerOrRemove
import kotlinx.parcelize.Parcelize

@Parcelize
data class Has(
    val id: Long? = null,
    val tags: List<Long> = emptyList(),
    val type: Long,
    val imagePath: String? = null,
    val createTime: Long = System.currentTimeMillis(),
    val isRemove: Boolean = false
): Parcelable {
    fun getTagList(tagTotalList: List<Tag>) = tagTotalList.filter { tags.contains(it.id) }

    fun checkSelected(hasList: List<Has>): Boolean {
        hasList.forEach {
            if (it.id == this.id) return true
        }
        return false
    }
}
fun List<Has>.selectType(type: Type): List<Has> {
    return if(type.id == null) {
        this
    } else {
        this.filter { it.type == type.id }
    }
}

fun List<Has>.unSelectType(typeRemoveList: List<Type>): List<Has> {
    return filter { typeRemoveList.map { it.id }.contains(it.type) }
}

fun List<Has>.selectTag(tags: List<Tag>): List<Has> {
    val selectedTagsId = tags.map { it.id!! }
    return if(tags.isEmpty()) {
        this
    } else {
        this.filter { has ->
            var isSelected = false
            has.tags.forEach { tagId ->
                if(selectedTagsId.contains(tagId)) {
                    isSelected = true
                    return@forEach
                }
            }
            isSelected
        }
    }
}

fun String.searchHasList(tagTotalList: List<Tag>, hasTotalList: List<Has>): List<Has> {
    val tagIdList = tagTotalList.filter { it.name.contains(this) }.map { it.id }
    return hasTotalList.filter { tagIdList.contains(it.id) }
}

fun String.searchSelectedTypeHasList(tagTotalList: List<Tag>, hasTotalList: List<Has>, type: Type, typeRemoveList: List<Type>): List<Has> {
    return when {
        type.id == null -> searchHasList(tagTotalList, hasTotalList)
        type.id == -1L -> searchHasList(tagTotalList, hasTotalList).unSelectType(typeRemoveList)
        isNotEmpty() -> searchHasList(tagTotalList, hasTotalList).selectType(type)
        isEmpty() -> hasTotalList.selectType(type)
        else -> emptyList()
    }
}

fun List<Has>.getSelectedTypeAndTagHasList(selectedType: Type, selectedTags: List<Tag>, removeTypeList: List<Type>): List<Has> {
    return when {
        selectedType.id == -1L -> unSelectType(removeTypeList)
        else -> selectType(selectedType)
    }.selectTag(selectedTags)
}

fun List<Has>.checkTypeAndSelectHas(has: Has): List<Has> {
    return toMutableList().apply {
        when {
            firstOrNull { it.id == has.id }.isNotNull() -> offerOrRemove(has) { it.id == has.id }
            firstOrNull { it.type == has.type }.isNotNull() -> {
                offerOrRemove(has) { it.type == has.type }
                add(has)
            }
            else -> add(has)
        }
    }.sortedBy { it.type }
}