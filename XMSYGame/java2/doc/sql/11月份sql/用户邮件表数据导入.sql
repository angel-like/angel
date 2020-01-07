UPDATE sys_message_user a
INNER JOIN (
	SELECT
		b.id AS id,
		b.title AS title,
		b.content AS content,
		b.failure_time AS failure_time,
		b.effect_time AS effect_time,
		b.content_type as content_type,
   b.readonly as readonly
	FROM
		sys_message_management b
) c ON a.message_id = c.id
SET a.title = c.title,
a.content =c.content,
a.failure_time =c.failure_time,
a.effect_time = c.effect_time,
a.content_type =c.content_type,
a.readonly =c.readonly
;
