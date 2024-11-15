INSERT INTO publication_statuses (publication_status_id, description, name)
VALUES ('a047cdea-d972-4be5-a192-3788cec1bc9a',
        'The listing is live and visible to other users. This means it can be viewed, and buyers can contact the seller.',
        'ACTIVE');

INSERT INTO publication_statuses (publication_status_id, description, name)
VALUES ('8d7fd9e3-9bfa-4672-96a8-d704a860c703',
        'The listing is temporarily hidden but can be reactivated by the seller at any time. Useful if the seller wants to take a break from receiving inquiries.',
        'PAUSED');

INSERT INTO publication_statuses (publication_status_id, description, name)
VALUES ('7ab5ab24-bc92-4ce4-868d-aba5afbf14f3',
        'The listing is waiting for approval from admins or moderators before being visible to the public.',
        'PENDING_APPROVAL');

INSERT INTO publication_statuses (publication_status_id, description, name)
VALUES ('87d9f389-d1d9-4551-8f45-988cccd9d92d',
        'Some platforms automatically expire listings after a certain period. Sellers may need to renew or repost the listing if they want it active again.',
        'EXPIRED');