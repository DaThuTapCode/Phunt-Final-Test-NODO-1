create database if not exists intern_final_1;
use intern_final_1;

drop table if exists Student_Course;
drop table if exists Student;
drop table if exists Course;

create table Student
(
    id           bigint auto_increment,
    student_code varchar(10),
    name         nvarchar(100),
    email        varchar(255),
    img          text,
    created_at   datetime,
    updated_at   datetime,
    status       int,
    constraint pk_student primary key (id)
);

create table Course
(
    id          bigint auto_increment,
    course_code varchar(10),
    title       nvarchar(255),
    description nvarchar(255),
    img         text,
    created_at  datetime,
    updated_at  datetime,
    status      int,
    constraint pk_course primary key (id)
);

create table Student_Course
(
    student_id bigint,
    course_id  bigint,
    created_at datetime,
    updated_at datetime,
    status     int,
    constraint fk_student_course_student foreign key (student_id) references student (id),
    constraint fk_student_course_course foreign key (course_id) references course (id),
    constraint pk_student_course primary key (student_id, course_id)
);

select *
from student;
select *
from course;
select *
from student_course;
select *
from student_course
where student_id = 1;

SELECT S.ID     AS STUDENT_ID,
       S.NAME,
       S.STATUS AS STATUS_STUDENT,
       C.ID     AS COURSE_ID,
       C.TITLE
FROM STUDENT S
         LEFT JOIN STUDENT_COURSE SC ON SC.STUDENT_ID = S.ID
         LEFT JOIN COURSE C ON C.ID = SC.COURSE_ID
WHERE S.STATUS = 1;

insert into student(student_code, name, email, img, created_at, updated_at, status)
values ('SD00000001', N'Nguyễn Trọng Phú', N'ntpdth2004@gmail.com', N'anhcuaphu.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000002', N'Nguyễn Thanh Thảo', N'thaont2004@gmail.com', N'anhcuathao.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000003', N'Ngô Hồng Ngọc', N'ngocnh2004@gmail.com', N'anhcuangoc.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000004', N'Đàm Thị Diệu Linh', N'linhdtd2004@gmail.com', N'anhcualinh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000005', N'Trần Minh Anh', N'minhanht2004@gmail.com', N'anhcuaanh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000006', N'Vũ Đức Long', N'longvd2004@gmail.com', N'anhcualong.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000007', N'Phạm Bích Ngọc', N'ngocpb2004@gmail.com', N'anhcuabichngoc.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000008', N'Lê Anh Quân', N'quanla2004@gmail.com', N'anhcuaquan.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000009', N'Nguyễn Minh Phương', N'phuongnm2004@gmail.com', N'anhcuaphuong.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000010', N'Nguyễn Văn Đức', N'ducnv2004@gmail.com', N'anhcuaduc.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000011', N'Phan Hoàng Linh', N'linhph2004@gmail.com', N'anhcualinhph.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000012', N'Nguyễn Thị Hồng', N'hongnt2004@gmail.com', N'anhcuahong.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000013', N'Vũ Văn Tùng', N'tungvv2004@gmail.com', N'anhcuatung.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000014', N'Trịnh Thị Thu', N'thutt2004@gmail.com', N'anhcuathu.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000015', N'Lê Thanh Sơn', N'sonlt2004@gmail.com', N'anhcuason.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000016', N'Nguyễn Thu Trang', N'trangnt2004@gmail.com', N'anhcuatrang.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000017', N'Nguyễn Mạnh Cường', N'cuongnm2004@gmail.com', N'anhcuacuong.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000018', N'Trần Bảo Khánh', N'khanhtb2004@gmail.com', N'anhcuakhanh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000019', N'Vũ Minh Tú', N'tumv2004@gmail.com', N'anhcuatu.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000020', N'Phạm Thị Minh Châu', N'chaumtp2004@gmail.com', N'anhcuachau.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000021', N'Lê Thị Thu Hằng', N'hangltt2004@gmail.com', N'anhcuahang.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000022', N'Nguyễn Văn Dũng', N'dungnv2004@gmail.com', N'anhcuadung.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000023', N'Vũ Thị Ngọc Hạnh', N'hanhvt2004@gmail.com', N'anhcuahanh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000024', N'Trịnh Văn Thành', N'thanhtv2004@gmail.com', N'anhcuathanh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000025', N'Phạm Văn Nam', N'namnv2004@gmail.com', N'anhcuanam.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000026', N'Lê Minh Tuấn', N'tuanlm2004@gmail.com', N'anhcuatuan.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000027', N'Nguyễn Văn Hoàng', N'hoangnv2004@gmail.com', N'anhcuahoang.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000028', N'Trương Thị Lan', N'lantt2004@gmail.com', N'anhcualan.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000029', N'Phạm Thị Hương', N'huongpt2004@gmail.com', N'anhcuahuong.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000030', N'Vũ Văn Sơn', N'sonvv2004@gmail.com', N'anhcuasonvv.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000031', N'Nguyễn Thị Lan', N'lannt2004@gmail.com', N'anhcualannt.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000032', N'Lê Minh Khoa', N'khoalm2004@gmail.com', N'anhcuakhoa.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000033', N'Trần Văn Bình', N'binhtv2004@gmail.com', N'anhcuabinh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000034', N'Nguyễn Đức Toàn', N'toandn2004@gmail.com', N'anhcuatoan.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000035', N'Trần Văn Nam', N'namtv2004@gmail.com', N'anhcuanamtv.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000036', N'Nguyễn Hồng Sơn', N'sonnh2004@gmail.com', N'anhcuasonnh.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000037', N'Lê Văn Hải', N'hailv2004@gmail.com', N'anhcuahai.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000038', N'Vũ Thị Mai', N'maivt2004@gmail.com', N'anhcuamai.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000039', N'Nguyễn Văn Bình', N'binhnv2004@gmail.com', N'anhcuabinhnv.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('SD00000040', N'Lê Hoàng Anh', N'anhla2004@gmail.com', N'anhcuaanhla.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1);

insert into course(course_code, title, description, img, created_at, updated_at, status)
values ('C000000001', N'Lập trình Java 5', N'Khóa học java 5', 'anhcourse1.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('C000000002', N'Lập trình Java Web', N'Khóa học java web sử dụng Spring boot', 'anhcourse2.jpg',
        '2024-08-29 00:00:00', '2024-08-29 00:00:00', 1),
       ('C000000003', N'Lập trình C#', N'Khóa học lập trình C# cơ bản', 'anhcourse3.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('C000000004', N'Lập trình Python', N'Khóa học lập trình Python cho người mới bắt đầu', 'anhcourse4.jpg',
        '2024-08-29 00:00:00', '2024-08-29 00:00:00', 1),
       ('C000000005', N'Lập trình Web với Django', N'Khóa học phát triển web với Django framework', 'anhcourse5.jpg',
        '2024-08-29 00:00:00', '2024-08-29 00:00:00', 1),
       ('C000000006', N'Lập trình C++', N'Khóa học lập trình C++ từ cơ bản đến nâng cao', 'anhcourse6.jpg',
        '2024-08-29 00:00:00', '2024-08-29 00:00:00', 1),
       ('C000000007', N'Phát triển ứng dụng di động với Flutter',
        N'Khóa học xây dựng ứng dụng di động đa nền tảng với Flutter', 'anhcourse7.jpg', '2024-08-29 00:00:00',
        '2024-08-29 00:00:00', 1),
       ('C000000008', N'Trí tuệ nhân tạo với Python', N'Khóa học về trí tuệ nhân tạo và học máy sử dụng Python',
        'anhcourse8.jpg', '2024-08-29 00:00:00', '2024-08-29 00:00:00', 1);

insert into Student_Course(student_id, course_id, created_at, updated_at, status)
values (1, 1, current_timestamp(), current_timestamp(), 1),
       (1, 2, current_timestamp(), current_timestamp(), 1),
       (2, 2, current_timestamp(), current_timestamp(), 1),
       (3, 2, current_timestamp(), current_timestamp(), 1),
       (4, 1, current_timestamp(), current_timestamp(), 0),
       (2, 3, current_timestamp(), current_timestamp(), 1),
       (3, 3, current_timestamp(), current_timestamp(), 1),
       (4, 3, current_timestamp(), current_timestamp(), 1),
       (5, 4, current_timestamp(), current_timestamp(), 1),
       (6, 4, current_timestamp(), current_timestamp(), 1),
       (7, 5, current_timestamp(), current_timestamp(), 1),
       (8, 6, current_timestamp(), current_timestamp(), 1),
       (1, 3, current_timestamp(), current_timestamp(), 1),
       (2, 4, current_timestamp(), current_timestamp(), 1),
       (3, 5, current_timestamp(), current_timestamp(), 1),
       (4, 6, current_timestamp(), current_timestamp(), 1),
       (5, 7, current_timestamp(), current_timestamp(), 1),
       (6, 7, current_timestamp(), current_timestamp(), 1),
       (7, 8, current_timestamp(), current_timestamp(), 1),
       (8, 8, current_timestamp(), current_timestamp(), 1),
       (1, 4, current_timestamp(), current_timestamp(), 1),
       (2, 5, current_timestamp(), current_timestamp(), 1),
       (3, 6, current_timestamp(), current_timestamp(), 1),
       (4, 7, current_timestamp(), current_timestamp(), 1),
       (5, 8, current_timestamp(), current_timestamp(), 1),
       (6, 1, current_timestamp(), current_timestamp(), 0),
       (7, 2, current_timestamp(), current_timestamp(), 0),
       (8, 3, current_timestamp(), current_timestamp(), 0),
       (1, 5, current_timestamp(), current_timestamp(), 1),
       (2, 6, current_timestamp(), current_timestamp(), 1),
       (3, 7, current_timestamp(), current_timestamp(), 1),
       (4, 8, current_timestamp(), current_timestamp(), 1),
       (5, 1, current_timestamp(), current_timestamp(), 1),
       (6, 2, current_timestamp(), current_timestamp(), 1),
       (7, 3, current_timestamp(), current_timestamp(), 1),
       (8, 4, current_timestamp(), current_timestamp(), 1);


