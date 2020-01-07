UPDATE sys_message_prop a
INNER JOIN (
	SELECT
		b.id AS id,
    b.message_id AS messageId
	FROM
		sys_message_user b
) c ON a.message_id = c.messageId
SET a.message_id = c.id
;