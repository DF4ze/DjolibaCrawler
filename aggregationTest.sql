SELECT distinct 
    course.courseid as courseId,
	rapport.courseid as rapportId,
    arrivee.courseid as arriveeId,
    cote.courseid as coteId,
    partant.courseid as partantId
FROM courses.rapport 
right join courses.course on course.courseid = rapport.courseid
left join courses.arrivee on arrivee.courseid = course.courseid
left join courses.cote on cote.courseid = course.courseid
left join courses.partant on partant.courseid = course.courseid
;
-- where course.courseid is null;

select * from courses.course;
select count(distinct courseid) from courses.course_complete;

select distinct rapport.courseid from courses.rapport where courseid = 1379439;