@import dto.UsersPage
@import utils.NamedRoutes
@param UsersPage page

@template.layout.page(
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                @if(page.getUsers().isEmpty())
                    <p>Пока не добавлено ни одного пользователя</p>
                @endif
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Level</th>
                    </tr>
                    </thead>
                    @for(var user : page.getUsers())
                        <tbody>
                        <tr>
                            <th scope="row">${user.getId()}</th>
                            <td><a href="${NamedRoutes.userPath(user.getId())}">${user.getName()}</a></td>
                            <th scope="row">${user.getEmail()}</th>
                            <th scope="row">${user.getLevel()}</th>
                        </tr>
                        </tbody>
                    @endfor
                </table>
            </div>
        </section>
    </main>
`
)