document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginform');

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const formData = new FormData();
        formData.append('email', email);
        formData.append('password', password);

        fetch('http://localhost:8080/projet_jakarta_war_exploded/logincontroller', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Login failed');
                }
                return response.json();
            })
            .then(data => {
                document.cookie = "data" + data ;
                localStorage.setItem('jwtToken', data);
                window.location.href = '/index.jsp';
            })
            .catch(error => {
                console.error('Login error:', error);
                alert('Login failed');
            });
    });
});