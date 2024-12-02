document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginform');

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;



        fetch('http://http://localhost:8083/project_jee/logincontroller', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Login failed');
                }
                return response.json();
            })
            .then(data => {
                console.log('Login successful ', data);
                localStorage.setItem('login', JSON.stringify(data));
                document.cookie = 'login' + JSON.stringify(data);
                window.location.href = './Admindashboard.html';
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});