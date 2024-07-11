import request from '@/utils/request'

// 查询活动评论关系列表
export function listRelation(query) {
  return request({
    url: '/system/volunteerOrganization/relation/list',
    method: 'get',
    params: query
  })
}

// 查询活动评论关系详细
export function getRelation(id) {
  return request({
    url: '/system/volunteerOrganization/relation/' + id,
    method: 'get'
  })
}

// 新增活动评论关系
export function addRelation(data) {
  return request({
    url: '/system/volunteerOrganization/relation',
    method: 'post',
    data: data
  })
}

// 修改活动评论关系
export function updateRelation(data) {
  return request({
    url: '/system/volunteerOrganization/relation',
    method: 'put',
    data: data
  })
}

// 删除活动评论关系
export function delRelation(id) {
  return request({
    url: '/system/volunteerOrganization/relation/' + id,
    method: 'delete'
  })
}
